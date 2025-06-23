package com.bruno.controle_estoque.dto.request;

import com.bruno.controle_estoque.enums.Categoria;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProdutoRequestDTO {
    
    @NotNull
    private String nome;

    @NotNull
    private String descricao;

    @NotNull
    private Categoria categoria;
    
    @Positive
    @NotNull
    private BigDecimal precoUnitario;
    
    @PositiveOrZero
    private Integer quantEstoque;
    
    @NotNull
    private String sku;
    
    @NotNull
    private LocalDateTime dataCadastro;
}
