package com.bruno.controle_estoque.dto.response;

import com.bruno.controle_estoque.enums.TypeMovement;
import com.bruno.controle_estoque.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovementResponseDTO {
    private Long id;
    private Product product;
    private TypeMovement typeMovement;
    private Integer amount;
    private LocalDateTime dataMovement;
}
