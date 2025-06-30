package com.bruno.controle_estoque.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockValueResponseDTO {

    private BigDecimal totalValue;

}
