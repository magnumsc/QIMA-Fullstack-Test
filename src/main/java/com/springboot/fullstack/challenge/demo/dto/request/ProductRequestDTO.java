package com.springboot.fullstack.challenge.demo.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    @Min(1)
    @Max(9999)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9\\- ]{1,255}$", message = "Name can only contain letters, numbers and spaces")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9()\\.\\- ]{0,255}$", message = "Description can only contain letters, numbers, parenthesis, dots and spaces")
    @Size(min = 0, max = 255, message = "Description must be between 0 and 255 characters")
    private String description;

    @Min(0)
    @Max(999999)
    private Double price;

    @Pattern(regexp = "^https?://.+\\.(jpg|jpeg|png|gif)", message = "Image URL must start with http:// or https://")
    @Size(min = 0, max = 255, message = "Image URL must be between 0 and 255 characters")
    private String imageUrl;

    @Min(1)
    @Max(9999)
    private Integer category;

    @Min(0)
    @Max(99999)
    private Integer stockQuantity;

    private Boolean available;
}
