package com.bruno.controle_estoque.model;

import com.bruno.controle_estoque.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    private Integer quantStock;

    @Column(unique = true, nullable = false)
    private String sku;

    @Column(nullable = false)
    private LocalDateTime dateRegistration;

    private LocalDateTime lastUpdate;

}
