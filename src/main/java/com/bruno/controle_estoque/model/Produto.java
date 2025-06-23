package com.bruno.controle_estoque.model;

import com.bruno.controle_estoque.enums.Categoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_produtos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Column(nullable = false)
    private BigDecimal precoUnitario;

    private Integer quantEstoque;

    @Column(unique = true, nullable = false)
    private String sku;

    @Column(nullable = false)
    private LocalDateTime dataCadastro;
    private LocalDateTime ultimaAtualizacao;


}
