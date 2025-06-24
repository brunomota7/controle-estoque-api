package com.bruno.controle_estoque.repository;

import com.bruno.controle_estoque.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findBySku(String sku);
}
