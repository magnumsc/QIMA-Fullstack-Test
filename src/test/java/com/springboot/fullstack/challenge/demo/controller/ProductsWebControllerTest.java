package com.springboot.fullstack.challenge.demo.controller;

import com.springboot.fullstack.challenge.demo.config.security.enums.UserRoles;
import com.springboot.fullstack.challenge.demo.config.security.principal.AuthPrincipal;
import com.springboot.fullstack.challenge.demo.config.security.service.AuthenticationService;
import com.springboot.fullstack.challenge.demo.dto.response.ProductResponseDTO;
import com.springboot.fullstack.challenge.demo.service.ProductsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductsWebControllerTest {

    @Mock
    private ProductsService productsService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private Model model;

    @Mock
    private AuthPrincipal authPrincipal;

    @InjectMocks
    private ProductsWebController productsWebController;

    @Test
    @DisplayName("getListOfProducts returns products view with products and admin status")
    void getListOfProductsReturnsProductsViewWithProductsAndAdminStatus() {
        var product = new ProductResponseDTO();
        product.setId(1L);
        product.setName("Product 1");
        when(productsService.getAllProducts()).thenReturn(List.of(product));
        when(authenticationService.hasRole(UserRoles.ADMIN, authPrincipal)).thenReturn(true);

        String viewName = productsWebController.getListOfProducts(authPrincipal, model);

        assertEquals("products", viewName);
        verify(model).addAttribute("products", List.of(product));
        verify(model).addAttribute("userIsAdmin", true);
    }
}