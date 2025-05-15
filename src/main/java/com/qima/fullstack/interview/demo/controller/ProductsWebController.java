package com.qima.fullstack.interview.demo.controller;

import com.qima.fullstack.interview.demo.config.security.enums.UserRoles;
import com.qima.fullstack.interview.demo.config.security.principal.AuthPrincipal;
import com.qima.fullstack.interview.demo.config.security.service.AuthenticationService;
import com.qima.fullstack.interview.demo.dto.response.ProductResponseDTO;
import com.qima.fullstack.interview.demo.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Validated
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsWebController {
    private final ProductsService productsService;
    private final AuthenticationService authenticationService;

    @GetMapping
    public String getListOfProducts(
            @AuthenticationPrincipal AuthPrincipal authPrincipal,
            Model model
    ) {
        List<ProductResponseDTO> products = productsService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("userIsAdmin", authenticationService.hasRole(UserRoles.ADMIN, authPrincipal));
        return "products";
    }
}
