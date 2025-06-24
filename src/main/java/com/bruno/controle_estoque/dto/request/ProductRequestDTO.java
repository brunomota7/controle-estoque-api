package com.bruno.controle_estoque.dto.request;

import com.bruno.controle_estoque.enums.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductRequestDTO {
    
    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Category category;
    
    @Positive
    @NotNull
    private BigDecimal unitPrice;
    
    @PositiveOrZero
    private Integer quantStock;
    
    @NotNull
    private String sku;

    private LocalDateTime dateRegistration;

}
