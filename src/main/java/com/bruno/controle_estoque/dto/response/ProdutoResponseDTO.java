package com.bruno.controle_estoque.dto.response;

import com.bruno.controle_estoque.enums.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Categoria categoria;
    private BigDecimal precoUnitario;
    private Integer quantEstoque;
    private String sku;
    private LocalDateTime dataCadastro;
    private LocalDateTime ultimaAtualizacao;
}
