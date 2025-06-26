package com.springboot.fullstack.challenge.demo.service;

import com.springboot.fullstack.challenge.demo.dto.request.ProductRequestDTO;
import com.springboot.fullstack.challenge.demo.dto.response.ProductResponseDTO;
import com.springboot.fullstack.challenge.demo.model.ProductModel;
import com.springboot.fullstack.challenge.demo.repository.CategoriesRepository;
import com.springboot.fullstack.challenge.demo.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;
    private final CategoriesRepository categoriesRepository;

    public List<ProductResponseDTO> getAllProducts() {
        return productsRepository.findAll().stream()
                .map(product -> new ProductResponseDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getImageUrl(),
                        product.getCategoryPath(),
                        product.getCategory() != null ? product.getCategory().getName() : null,
                        product.getCategory() != null ? product.getCategory().getId() : null,
                        product.getStock(),
                        Boolean.TRUE.equals(product.getAvailable()) ? "yes" : "no"
                )).collect(Collectors.toList());
    }

    public Boolean deleteProduct(Long id) {
        var product = productsRepository.findById(id);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }
        try {
            productsRepository.delete(product.get());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long addProduct(ProductRequestDTO productRequestDTO) {
        var product = mapToProductModel(productRequestDTO, null);
        product = productsRepository.save(product);
        return product.getId();
    }

    public ProductResponseDTO saveProduct(ProductRequestDTO productRequestDTO) {
        var product = productsRepository.findById(productRequestDTO.getId()).orElseThrow(
                () -> new IllegalArgumentException("Product not found"));
        product = mapToProductModel(productRequestDTO, product.getId());
        product = productsRepository.save(product);
        return mapToProductResponseDTO(product);
    }

    private ProductModel mapToProductModel(ProductRequestDTO productRequestDTO, Long id) {
        var category = categoriesRepository.findById(productRequestDTO.getCategory()).orElse(null);
        var product = new ProductModel();
        product.setId(id);
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setImageUrl(productRequestDTO.getImageUrl());
        product.setCategory(category);
        product.setStock(productRequestDTO.getStockQuantity());
        product.setAvailable(productRequestDTO.getAvailable());
        return product;
    }

    private ProductResponseDTO mapToProductResponseDTO(ProductModel product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl(),
                product.getCategoryPath(),
                product.getCategory() != null ? product.getCategory().getName() : null,
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getStock(),
                Boolean.TRUE.equals(product.getAvailable()) ? "yes" : "no"
        );
    }
}
