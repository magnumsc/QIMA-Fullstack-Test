package com.springboot.fullstack.challenge.demo.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private String categoryPath;
    private String categoryName;
    private Integer categoryId;
    private Integer stockQuantity;
    private String available;
}
