package com.springboot.fullstack.challenge.demo.repository;

import com.springboot.fullstack.challenge.demo.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductsRepository extends JpaRepository<ProductModel, Long> {
    // For future reference only, currently the query is not used as the screen handle the filtering in the front-end
    @Query(value = """
        WITH RECURSIVE CategoryTree AS (
            SELECT c.id
            FROM "springboot_fullstack_challenge".categories c
            WHERE c.id = :category
            UNION ALL
            SELECT c.id
            FROM "springboot_fullstack_challenge".categories c
            INNER JOIN CategoryTree ct ON c.parent = ct.id
        )
        SELECT p.*
        FROM "springboot_fullstack_challenge".products p
        WHERE :category IS NULL
            OR p.category IN (SELECT id FROM CategoryTree)
        ORDER BY p.id
    """, nativeQuery = true)
    List<ProductModel> findAllByCategory(Integer category);
}