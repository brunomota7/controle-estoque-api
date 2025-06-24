package com.bruno.controle_estoque.repository;

import com.bruno.controle_estoque.model.MovementStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<MovementStock, Long> {
}
