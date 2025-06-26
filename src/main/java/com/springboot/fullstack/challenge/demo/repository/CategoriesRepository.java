package com.springboot.fullstack.challenge.demo.repository;

import com.springboot.fullstack.challenge.demo.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<CategoryModel, Integer> {
}