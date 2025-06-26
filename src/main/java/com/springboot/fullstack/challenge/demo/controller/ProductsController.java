package com.springboot.fullstack.challenge.demo.controller;

import com.springboot.fullstack.challenge.demo.config.dto.BadRequestDTO;
import com.springboot.fullstack.challenge.demo.config.security.annotations.IsAdmin;
import com.springboot.fullstack.challenge.demo.dto.request.ProductRequestDTO;
import com.springboot.fullstack.challenge.demo.dto.response.NewProductResponseDTO;
import com.springboot.fullstack.challenge.demo.service.ProductsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductsService productsService;

    @IsAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(
            @Min(1)
            @Max(9999)
            @PathVariable Long id
    ) {
        try {
            var deleted = productsService.deleteProduct(id);
            if (deleted) {
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @IsAdmin
    @PostMapping
    public ResponseEntity<NewProductResponseDTO> addProduct(
            @Valid
            @RequestBody ProductRequestDTO productRequestDTO
    ) {
        var newProductId = productsService.addProduct(productRequestDTO);
        return ResponseEntity.ok(new NewProductResponseDTO(newProductId));
    }

    @IsAdmin
    @PutMapping
    public ResponseEntity<Object> saveProduct(
            @Valid
            @RequestBody ProductRequestDTO productRequestDTO
    ) {
        try {
            var updatedProductId = productsService.saveProduct(productRequestDTO);
            return ResponseEntity.ok(updatedProductId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new BadRequestDTO());
        }
    }
}
