package com.qima.fullstack.interview.demo.controller;

import com.qima.fullstack.interview.demo.dto.response.CategoryResponseDTO;
import com.qima.fullstack.interview.demo.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Validated
@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoriesService categoriesService;

    @GetMapping("/list")
    public ResponseEntity<List<CategoryResponseDTO>> getListOfProducts() {
        return ResponseEntity.ok(categoriesService.findAllCategories());
    }
}
