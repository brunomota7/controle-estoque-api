package com.bruno.controle_estoque.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockResponseDTO {

    private ProductInfos productInfos;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductInfos {
        private Long id;
        private String name;
        private Integer quantStock;
    }

}
