package com.bruno.controle_estoque.service;

import com.bruno.controle_estoque.dto.request.ProdutoRequestDTO;
import com.bruno.controle_estoque.dto.response.ProdutoResponseDTO;
import com.bruno.controle_estoque.exceptions.ProdutoNotFoundException;
import com.bruno.controle_estoque.mapper.ProdutoMapper;
import com.bruno.controle_estoque.model.Produto;
import com.bruno.controle_estoque.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public void addNovoProduto(ProdutoRequestDTO dto) {
        Produto produto = Produto.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .categoria(dto.getCategoria())
                .precoUnitario(dto.getPrecoUnitario())
                .quantEstoque(dto.getQuantEstoque())
                .sku(dto.getSku())
                .dataCadastro(dto.getDataCadastro())
                .build();

        produtoRepository.save(produto);
    }

    public List<ProdutoResponseDTO> listarTodosOsProdutos() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoMapper::toDTO)
                .toList();
    }

    public ProdutoResponseDTO getByProdutoById(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto de ID " + id + " não encontrado!"));

        return ProdutoMapper.toDTO(produto);
    }

}
