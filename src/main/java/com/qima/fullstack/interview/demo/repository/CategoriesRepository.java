package com.qima.fullstack.interview.demo.repository;

import com.qima.fullstack.interview.demo.model.CategoryModel;
import com.qima.fullstack.interview.demo.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<CategoryModel, Integer> {
}