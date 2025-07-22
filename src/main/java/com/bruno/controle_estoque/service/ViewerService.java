package com.bruno.controle_estoque.service;

import com.bruno.controle_estoque.dto.response.MovementResponseDTO;
import com.bruno.controle_estoque.dto.response.ProductResponseDTO;
import com.bruno.controle_estoque.enums.Category;
import com.bruno.controle_estoque.enums.Roles;
import com.bruno.controle_estoque.enums.TypeMovement;
import com.bruno.controle_estoque.exceptions.InvalidRoleException;
import com.bruno.controle_estoque.exceptions.ProductNotFoundException;
import com.bruno.controle_estoque.mapper.MovementMapper;
import com.bruno.controle_estoque.mapper.ProductMapper;
import com.bruno.controle_estoque.model.Product;
import com.bruno.controle_estoque.model.Users;
import com.bruno.controle_estoque.repository.MovementRepository;
import com.bruno.controle_estoque.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class ViewerService {

    @Autowired private AuthService authService;
    @Autowired private ProductRepository productRepository;
    @Autowired private MovementRepository movementRepository;

    public Page<ProductResponseDTO> viewProducts(
            String name,
            Category category,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    ) {
        log.info("Visualizando produtos com filtros - Nome: {}, Categoria: {}, Preço Mínimo: {}, Preço Máximo: {}",
                name, category, minPrice, maxPrice);
        return productRepository.filterToSearchProducts(name, category, minPrice, maxPrice, pageable)
                .map(ProductMapper::toDTO);
    }

    public ProductResponseDTO detailProducts(Long id) {
        log.info("Buscando detalhes do produto de ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Produto com ID {} não encontrado!", id);
                    return new ProductNotFoundException("Produto de ID " + id + " não encontrado!");
                });

        return ProductMapper.toDTO(product);
    }

    public Page<ProductResponseDTO> getAllProductsLowStock(Pageable pageable) {
        log.info("Listando produtos com estoque baixo.");
        return movementRepository.productsWhithLowStock(pageable)
                .map(ProductMapper::toDTO);
    }

    public List<MovementResponseDTO> getAllMovementsEntry() {
        log.info("Listando todos os movimentos de ENTRADA.");
        return movementRepository.findByTypeMovement(TypeMovement.ENTRADA)
                .stream()
                .map(MovementMapper::toDTO)
                .toList();
    }

    public List<MovementResponseDTO> getAllMovementsExit() {
        log.info("Listando todos os movimentos de SAÍDA.");
        return movementRepository.findByTypeMovement(TypeMovement.SAIDA)
                .stream()
                .map(MovementMapper::toDTO)
                .toList();
    }

    public List<MovementResponseDTO> getProductMovementHistory(Long id) {
        log.info("Buscando histórico de movimentações do produto de ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Produto com ID {} não encontrado ao buscar histórico de movimentações.", id);
                    return new ProductNotFoundException("Produto de ID " + id + " não encontrado!");
                });

        return movementRepository.findByProduct(product)
                .stream()
                .map(MovementMapper::toDTO)
                .toList();
    }
}
