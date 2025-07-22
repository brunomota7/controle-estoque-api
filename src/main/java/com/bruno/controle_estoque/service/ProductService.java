package com.bruno.controle_estoque.service;

import com.bruno.controle_estoque.dto.request.ProductRequestDTO;
import com.bruno.controle_estoque.dto.response.ProductResponseDTO;
import com.bruno.controle_estoque.enums.Category;
import com.bruno.controle_estoque.exceptions.ProductNotFoundException;
import com.bruno.controle_estoque.mapper.ProductMapper;
import com.bruno.controle_estoque.model.Product;
import com.bruno.controle_estoque.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void addNewProduct(ProductRequestDTO dto) {
        log.info("Iniciando processo de adição de novo produto: {}", dto.getName());

        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .unitPrice(dto.getUnitPrice())
                .quantStock(dto.getQuantStock())
                .sku(dto.getSku())
                .dateRegistration(LocalDateTime.now())
                .build();

        productRepository.save(product);

        log.info("Produto '{}' adicionado com sucesso. ID: {}", product.getName(), product.getId());
    }

    public Page<ProductResponseDTO> searchProducts(
            String name,
            Category category,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    ) {
        log.info("Realizando busca de produtos com filtros - Nome: {}, Categoria: {}, Preço Mínimo: {}, Preço Máximo: {}",
                name, category, minPrice, maxPrice);

        Page<ProductResponseDTO> result = productRepository
                .filterToSearchProducts(name, category, minPrice, maxPrice, pageable)
                .map(ProductMapper::toDTO);

        log.info("Busca finalizada. {} produtos encontrados.", result.getTotalElements());
        return result;
    }

    public ProductResponseDTO getProductById(Long id) {
        log.info("Buscando produto pelo ID: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Produto de ID {} não encontrado!", id);
                    return new ProductNotFoundException("Produto de ID " + id + " não encontrado!");
                });

        log.info("Produto encontrado: {}", product.getName());
        return ProductMapper.toDTO(product);
    }

    public void updateProducts(Long id, ProductRequestDTO dto) {
        log.info("Iniciando atualização do produto de ID: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Produto de ID {} não encontrado para atualização!", id);
                    return new ProductNotFoundException("Produto de ID " + id + " não encontrado!");
                });

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setUnitPrice(dto.getUnitPrice());
        product.setQuantStock(dto.getQuantStock());
        product.setSku(dto.getSku());
        product.setLastUpdate(LocalDateTime.now());

        productRepository.save(product);

        log.info("Produto '{}' (ID: {}) atualizado com sucesso.", product.getName(), product.getId());
    }

    public void deleteProduct(Long id) {
        log.info("Solicitação para exclusão do produto de ID: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Produto de ID {} não encontrado para exclusão!", id);
                    return new ProductNotFoundException("Produto de ID " + id + " não encontrado!");
                });

        productRepository.delete(product);

        log.info("Produto '{}' (ID: {}) excluído com sucesso.", product.getName(), product.getId());
    }

}
