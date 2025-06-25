package com.bruno.controle_estoque.repository;

import com.bruno.controle_estoque.enums.Category;
import com.bruno.controle_estoque.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findBySku(String sku);
    @Query("SELECT p FROM Product p WHERE " +
           "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND" +
           "(:category IS NULL OR p.category = :category) AND" +
           "(:minPrice IS NULL OR p.unitPrice >= :minPrice) AND" +
           "(:maxPrice IS NULL OR p.unitPrice <= :maxPrice) AND" +
           "(:minStock IS NULL OR p.quantStock >= :minStock)"
    )
    Page<Product> filterToSearchProducts(
            @Param("name") String name,
            @Param("category") Category category,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minStock") Integer minStock,
            Pageable pageable
    );
}
