package com.bruno.controle_estoque.mapper;

import com.bruno.controle_estoque.dto.response.ProdutoResponseDTO;
import com.bruno.controle_estoque.model.Produto;

public class ProdutoMapper {

    public static ProdutoResponseDTO toDTO(Produto produto) {
        var builder = ProdutoResponseDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .categoria(produto.getCategoria())
                .precoUnitario(produto.getPrecoUnitario())
                .quantEstoque(produto.getQuantEstoque())
                .sku(produto.getSku())
                .dataCadastro(produto.getDataCadastro())
                .ultimaAtualizacao(produto.getUltimaAtualizacao());

        return builder.build();
    }

}
