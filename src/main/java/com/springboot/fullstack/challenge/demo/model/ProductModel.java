package com.springboot.fullstack.challenge.demo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "id")
    private CategoryModel category;
    private String description;
    private String imageUrl;
    private Double price;
    private Integer stock;
    private Boolean available;

    @Transient
    public String getCategoryPath() {
        if (category == null) {
            return null;
        }
        var cat = category;
        StringBuilder path = new StringBuilder(cat.getName());
        while (cat.getParent() != null) {
            cat = cat.getParent();
            path.insert(0, cat.getName() + " > ");
        }
        return path.toString();
    }
}
