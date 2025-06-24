package com.bruno.controle_estoque.repository;

import com.bruno.controle_estoque.enums.TypeMovement;
import com.bruno.controle_estoque.model.MovementStock;
import com.bruno.controle_estoque.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovementRepository extends JpaRepository<MovementStock, Long> {
    List<MovementStock> findByTypeMovement(TypeMovement typeMovement);
    List<MovementStock> findByProduct(Product product);
}
