package com.qima.fullstack.interview.demo.service;

import com.qima.fullstack.interview.demo.dto.response.CategoryResponseDTO;
import com.qima.fullstack.interview.demo.repository.CategoriesRepository;
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
