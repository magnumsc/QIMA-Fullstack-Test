package com.springboot.fullstack.challenge.demo.controller;

import com.springboot.fullstack.challenge.demo.config.dto.BadRequestDTO;
import com.springboot.fullstack.challenge.demo.dto.request.ProductRequestDTO;
import com.springboot.fullstack.challenge.demo.dto.response.NewProductResponseDTO;
import com.springboot.fullstack.challenge.demo.dto.response.ProductResponseDTO;
import com.springboot.fullstack.challenge.demo.service.ProductsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductsControllerTest {

    @Mock
    private ProductsService productsService;

    @InjectMocks
    private ProductsController productsController;

    @Test
    @DisplayName("deleteProduct returns OK when product is successfully deleted")
    void deleteProductReturnsOKWhenProductIsSuccessfullyDeleted() {
        when(productsService.deleteProduct(1L)).thenReturn(true);

        ResponseEntity<Boolean> response = productsController.deleteProduct(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody());
    }

    @Test
    @DisplayName("deleteProduct returns Not Found when product is not found")
    void deleteProductReturnsNotFoundWhenProductIsNotFound() {
        when(productsService.deleteProduct(1L)).thenReturn(false);

        ResponseEntity<Boolean> response = productsController.deleteProduct(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteProduct returns Bad Request when exception is thrown")
    void deleteProductReturnsBadRequestWhenExceptionIsThrown() {
        when(productsService.deleteProduct(1L)).thenThrow(new IllegalArgumentException());

        ResponseEntity<Boolean> response = productsController.deleteProduct(1L);

        assertEquals(400, response.getStatusCodeValue());
        assertFalse(response.getBody());
    }

    @Test
    @DisplayName("addProduct returns new product ID when product is successfully added")
    void addProductReturnsNewProductIdWhenProductIsSuccessfullyAdded() {
        var productRequestDTO = new ProductRequestDTO();
        when(productsService.addProduct(productRequestDTO)).thenReturn(1L);

        ResponseEntity<NewProductResponseDTO> response = productsController.addProduct(productRequestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    @DisplayName("saveProduct returns updated product ID when product is successfully saved")
    void saveProductReturnsUpdatedProductIdWhenProductIsSuccessfullySaved() {
        var productRequestDTO = new ProductRequestDTO();
        when(productsService.saveProduct(productRequestDTO)).thenReturn(new ProductResponseDTO());

        ResponseEntity<Object> response = productsController.saveProduct(productRequestDTO);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("saveProduct returns Bad Request when exception is thrown")
    void saveProductReturnsBadRequestWhenExceptionIsThrown() {
        var productRequestDTO = new ProductRequestDTO();
        when(productsService.saveProduct(productRequestDTO)).thenThrow(new IllegalArgumentException());

        ResponseEntity<Object> response = productsController.saveProduct(productRequestDTO);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof BadRequestDTO);
    }
}