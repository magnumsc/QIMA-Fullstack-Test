package com.qima.fullstack.interview.demo.repository;

import com.qima.fullstack.interview.demo.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductsRepository extends JpaRepository<ProductModel, Long> {
    @Query(value = """
        WITH RECURSIVE CategoryTree AS (
            SELECT c.id
            FROM "qima_interview".categories c
            WHERE c.id = :category
            UNION ALL
            SELECT c.id
            FROM "qima_interview".categories c
            INNER JOIN CategoryTree ct ON c.parent = ct.id
        )
        SELECT p.*
        FROM "qima_interview".products p
        WHERE :category IS NULL
            OR p.category IN (SELECT id FROM CategoryTree)
        ORDER BY p.id
    """, nativeQuery = true)
    List<ProductModel> findAllByCategory(Integer category);
}