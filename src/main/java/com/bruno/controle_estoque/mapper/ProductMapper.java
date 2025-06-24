package com.bruno.controle_estoque.mapper;

import com.bruno.controle_estoque.dto.response.ProductResponseDTO;
import com.bruno.controle_estoque.model.Product;

public class ProductMapper {

    public static ProductResponseDTO toDTO(Product product) {
        var builder = ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .unitPrice(product.getUnitPrice())
                .quantStock(product.getQuantStock())
                .sku(product.getSku())
                .dateRegistration(product.getDateRegistration())
                .lastUpdate(product.getLastUpdate());

        return builder.build();
    }

}
