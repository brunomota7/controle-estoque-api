package com.bruno.controle_estoque.dto.request;

import com.bruno.controle_estoque.enums.Categoria;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProdutoRequestDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Categoria categoria;
    private BigDecimal precoUnitario;
    private Integer quantEstoque;
    private String sku;
    private LocalDateTime dataCadastro;
}
