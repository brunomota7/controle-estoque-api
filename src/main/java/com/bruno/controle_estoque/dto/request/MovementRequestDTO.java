package com.bruno.controle_estoque.dto.request;

import com.bruno.controle_estoque.enums.TypeMovement;
import com.bruno.controle_estoque.model.Product;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovementRequestDTO {
    private Product product;
    private TypeMovement typeMovement;
    private Integer amount;
    private LocalDateTime dataMovement;
}
