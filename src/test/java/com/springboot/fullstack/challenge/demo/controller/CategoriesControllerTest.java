package com.springboot.fullstack.challenge.demo.controller;

import com.springboot.fullstack.challenge.demo.dto.response.CategoryResponseDTO;
import com.springboot.fullstack.challenge.demo.service.CategoriesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriesControllerTest {

    @Mock
    private CategoriesService categoriesService;

    @InjectMocks
    private CategoriesController categoriesController;

    @Test
    @DisplayName("getListOfProducts returns list of categories when service has data")
    void getListOfProductsReturnsListOfCategoriesWhenServiceHasData() {
        var category = new CategoryResponseDTO(1, "Electronics");
        when(categoriesService.findAllCategories()).thenReturn(List.of(category));

        ResponseEntity<List<CategoryResponseDTO>> response = categoriesController.getListOfProducts();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Electronics", response.getBody().get(0).getName());
    }

    @Test
    @DisplayName("getListOfProducts returns empty list when service has no data")
    void getListOfProductsReturnsEmptyListWhenServiceHasNoData() {
        when(categoriesService.findAllCategories()).thenReturn(Collections.emptyList());

        ResponseEntity<List<CategoryResponseDTO>> response = categoriesController.getListOfProducts();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().size());
    }
}