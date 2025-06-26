package com.springboot.fullstack.challenge.demo.service;

import com.springboot.fullstack.challenge.demo.dto.response.CategoryResponseDTO;
import com.springboot.fullstack.challenge.demo.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;

    public List<CategoryResponseDTO> findAllCategories() {
        return categoriesRepository.findAll().stream()
                .map(product -> CategoryResponseDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .build()
                ).collect(Collectors.toList());
    }
}
