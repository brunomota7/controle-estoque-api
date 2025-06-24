package com.bruno.controle_estoque.mapper;

import com.bruno.controle_estoque.dto.response.ProductStockResponseDTO;
import com.bruno.controle_estoque.model.Product;

public class ProductStockMapper {

    public static ProductStockResponseDTO toDTO(Product product) {
        var builder = ProductStockResponseDTO.builder()
                .productInfos(ProductStockResponseDTO.ProductInfos.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .quantStock(product.getQuantStock())
                        .build()
                );

        return builder.build();
    }

}
