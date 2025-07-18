package com.bruno.controle_estoque.model;

import com.bruno.controle_estoque.enums.TypeMovement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_movement_stock")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovementStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private TypeMovement typeMovement;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private LocalDateTime dataMovement;

}
