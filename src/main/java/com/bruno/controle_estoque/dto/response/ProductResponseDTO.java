package com.bruno.controle_estoque.dto.response;

import com.bruno.controle_estoque.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Category category;
    private BigDecimal unitPrice;
    private Integer quantStock;
    private String sku;
    private LocalDateTime dateRegistration;
    private LocalDateTime lastUpdate;
}
