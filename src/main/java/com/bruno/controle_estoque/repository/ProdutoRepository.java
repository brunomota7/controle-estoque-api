package com.bruno.controle_estoque.repository;

import com.bruno.controle_estoque.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Produto findBySku(String sku);
}
