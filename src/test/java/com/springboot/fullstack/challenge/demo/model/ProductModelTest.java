package com.springboot.fullstack.challenge.demo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductModelTest {

    @Test
    @DisplayName("getCategoryPath returns null when category is null")
    void getCategoryPathReturnsNullWhenCategoryIsNull() {
        ProductModel product = new ProductModel();
        product.setCategory(null);

        String result = product.getCategoryPath();

        assertNull(result);
    }

    @Test
    @DisplayName("getCategoryPath returns category name when category has no parent")
    void getCategoryPathReturnsCategoryNameWhenCategoryHasNoParent() {
        CategoryModel category = mock(CategoryModel.class);
        when(category.getName()).thenReturn("Electronics");

        ProductModel product = new ProductModel();
        product.setCategory(category);

        String result = product.getCategoryPath();

        assertEquals("Electronics", result);
    }

    @Test
    @DisplayName("getCategoryPath returns full path when category has parents")
    void getCategoryPathReturnsFullPathWhenCategoryHasParents() {
        CategoryModel parentCategory = mock(CategoryModel.class);
        when(parentCategory.getName()).thenReturn("Home");
        when(parentCategory.getParent()).thenReturn(null);

        CategoryModel category = mock(CategoryModel.class);
        when(category.getName()).thenReturn("Electronics");
        when(category.getParent()).thenReturn(parentCategory);

        ProductModel product = new ProductModel();
        product.setCategory(category);

        String result = product.getCategoryPath();

        assertEquals("Home > Electronics", result);
    }
}