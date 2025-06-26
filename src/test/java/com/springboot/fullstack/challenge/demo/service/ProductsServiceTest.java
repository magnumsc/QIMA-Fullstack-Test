package com.springboot.fullstack.challenge.demo.service;

import com.springboot.fullstack.challenge.demo.dto.request.ProductRequestDTO;
import com.springboot.fullstack.challenge.demo.dto.response.ProductResponseDTO;
import com.springboot.fullstack.challenge.demo.model.ProductModel;
import com.springboot.fullstack.challenge.demo.repository.CategoriesRepository;
import com.springboot.fullstack.challenge.demo.repository.ProductsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductsServiceTest {

    @Mock
    private ProductsRepository productsRepository;

    @Mock
    private CategoriesRepository categoriesRepository;

    @InjectMocks
    private ProductsService productsService;

    @Test
    @DisplayName("getFilteredProducts returns list of products for given category")
    void getAllProductsReturnsListOfProductsForGivenCategory() {
        var product = new ProductModel();
        product.setId(1L);
        product.setName("Product 1");
        when(productsRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponseDTO> result = productsService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals("Product 1", result.getFirst().getName());
    }

    @Test
    @DisplayName("getFilteredProducts returns empty list when no products found for given category")
    void getAllProductsReturnsEmptyListWhenNoProductsFoundForGivenCategory() {
        when(productsRepository.findAll()).thenReturn(List.of());
        List<ProductResponseDTO> result = productsService.getAllProducts();
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("deleteProduct returns true when product is successfully deleted")
    void deleteProductReturnsTrueWhenProductIsSuccessfullyDeleted() {
        var product = new ProductModel();
        product.setId(1L);
        when(productsRepository.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productsRepository).delete(any());
        Boolean result = productsService.deleteProduct(1L);
        assertTrue(result);
        verify(productsRepository, times(1)).delete(product);
    }

    @Test
    @DisplayName("deleteProduct throws exception when product not found")
    void deleteProductThrowsExceptionWhenProductNotFound() {
        when(productsRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> productsService.deleteProduct(1L));
    }

    @Test
    @DisplayName("addProduct returns product ID when product is successfully added")
    void addProductReturnsProductIdWhenProductIsSuccessfullyAdded() {
        var productRequestDTO = new ProductRequestDTO();
        var product = new ProductModel();
        product.setId(1L);
        when(productsRepository.save(any(ProductModel.class))).thenReturn(product);
        when(categoriesRepository.findById(any())).thenReturn(Optional.empty());
        Long result = productsService.addProduct(productRequestDTO);
        assertEquals(1L, result);
    }

    @Test
    @DisplayName("saveProduct returns updated product response when product is successfully saved")
    void saveProductReturnsUpdatedProductResponseWhenProductIsSuccessfullySaved() {
        var productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setId(1L);
        var product = new ProductModel();
        product.setId(1L);
        when(productsRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productsRepository.save(any(ProductModel.class))).thenReturn(product);
        when(categoriesRepository.findById(any())).thenReturn(Optional.empty());
        ProductResponseDTO result = productsService.saveProduct(productRequestDTO);

        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("saveProduct throws exception when product not found")
    void saveProductThrowsExceptionWhenProductNotFound() {
        var productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setId(1L);
        when(productsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> productsService.saveProduct(productRequestDTO));
    }
}
