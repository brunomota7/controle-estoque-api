package com.bruno.controle_estoque.repository;

import com.bruno.controle_estoque.model.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEstoque, Long> {
}
