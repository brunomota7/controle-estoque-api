package com.bruno.controle_estoque.mapper;

import com.bruno.controle_estoque.dto.response.MovementResponseDTO;
import com.bruno.controle_estoque.model.MovementStock;

public class MovementMapper {

    public static MovementResponseDTO toDTO(MovementStock movementStock) {
        var builder = MovementResponseDTO.builder()
                .id(movementStock.getId())
                .product(movementStock.getProduct())
                .typeMovement(movementStock.getTypeMovement())
                .amount(movementStock.getAmount())
                .dataMovement(movementStock.getDataMovement());

        return builder.build();
    }

}
