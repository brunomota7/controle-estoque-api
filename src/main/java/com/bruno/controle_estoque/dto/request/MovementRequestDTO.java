package com.bruno.controle_estoque.dto.request;

import com.bruno.controle_estoque.enums.TypeMovement;
import com.bruno.controle_estoque.model.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovementRequestDTO {

    @NotNull
    @Positive
    private Long productId;

    @NotNull
    @Min(value = 1)
    private Integer amount;
    private LocalDateTime dataMovement;
}
