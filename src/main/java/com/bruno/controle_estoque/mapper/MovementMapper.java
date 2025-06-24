package com.bruno.controle_estoque.mapper;

import com.bruno.controle_estoque.dto.response.MovementResponseDTO;
import com.bruno.controle_estoque.model.MovementStock;

public class MovementMapper {

    public static MovementResponseDTO toDTO(MovementStock movementStock) {
        var builder = MovementResponseDTO.builder()
                .id(movementStock.getId())
                .product(MovementResponseDTO.ProductInfos.builder()
                        .id(movementStock.getProduct().getId())
                        .name(movementStock.getProduct().getName())
                        .build()
                )
                .typeMovement(movementStock.getTypeMovement())
                .amount(movementStock.getAmount())
                .dataMovement(movementStock.getDataMovement());

        return builder.build();
    }

}
