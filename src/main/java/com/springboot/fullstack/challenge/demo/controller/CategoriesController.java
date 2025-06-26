package com.springboot.fullstack.challenge.demo.controller;

import com.springboot.fullstack.challenge.demo.dto.response.CategoryResponseDTO;
import com.springboot.fullstack.challenge.demo.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoriesService categoriesService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getListOfProducts() {
        return ResponseEntity.ok(categoriesService.findAllCategories());
    }
}
