package com.springboot.fullstack.challenge.demo.service;

import com.springboot.fullstack.challenge.demo.dto.response.CategoryResponseDTO;
import com.springboot.fullstack.challenge.demo.model.CategoryModel;
import com.springboot.fullstack.challenge.demo.repository.CategoriesRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriesServiceTest {

    @Mock
    private CategoriesRepository categoriesRepository;

    @InjectMocks
    private CategoriesService categoriesService;

    @Test
    @DisplayName("findAllCategories returns list of categories when repository has data")
    void findAllCategoriesReturnsListOfCategoriesWhenRepositoryHasData() {
        var category = new CategoryModel(1, "Electronics", null);
        when(categoriesRepository.findAll()).thenReturn(List.of(category));
        List<CategoryResponseDTO> result = categoriesService.findAllCategories();
        assertEquals(1, result.size());
        assertEquals("Electronics", result.getFirst().getName());
    }

    @Test
    @DisplayName("findAllCategories returns empty list when repository has no data")
    void findAllCategoriesReturnsEmptyListWhenRepositoryHasNoData() {
        when(categoriesRepository.findAll()).thenReturn(Collections.emptyList());
        List<CategoryResponseDTO> result = categoriesService.findAllCategories();
        assertEquals(0, result.size());
    }
}