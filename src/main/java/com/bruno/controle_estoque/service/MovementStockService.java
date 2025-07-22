package com.bruno.controle_estoque.service;

import com.bruno.controle_estoque.dto.request.MovementRequestDTO;
import com.bruno.controle_estoque.dto.response.MovementResponseDTO;
import com.bruno.controle_estoque.dto.response.ProductResponseDTO;
import com.bruno.controle_estoque.dto.response.ProductStockResponseDTO;
import com.bruno.controle_estoque.enums.TypeMovement;
import com.bruno.controle_estoque.exceptions.ProductNotFoundException;
import com.bruno.controle_estoque.exceptions.StockMovementException;
import com.bruno.controle_estoque.mapper.MovementMapper;
import com.bruno.controle_estoque.mapper.ProductMapper;
import com.bruno.controle_estoque.mapper.ProductStockMapper;
import com.bruno.controle_estoque.model.MovementStock;
import com.bruno.controle_estoque.model.Product;
import com.bruno.controle_estoque.repository.MovementRepository;
import com.bruno.controle_estoque.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class MovementStockService {

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private ProductRepository productRepository;

    private static final int LOW_STOCK_LIMIT = 10;

    @Transactional
    public void registerEntry(MovementRequestDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> {
                    log.error("Produto de ID {} não encontrado para entrada de estoque", dto.getProductId());
                    return new ProductNotFoundException("Produto de ID " + dto.getProductId() + " não encontrado para movimentação de entrada!");
                });

        MovementStock movement = MovementStock.builder()
                .product(product)
                .typeMovement(TypeMovement.ENTRADA)
                .amount(dto.getAmount())
                .dataMovement(LocalDateTime.now())
                .build();

        movementRepository.save(movement);

        product.setQuantStock(product.getQuantStock() + dto.getAmount());
        product.setLastUpdate(LocalDateTime.now());
        productRepository.save(product);

        log.info("Entrada registrada: Produto '{}', Quantidade adicionada: {}. Novo estoque: {}",
                product.getName(), dto.getAmount(), product.getQuantStock());
    }

    @Transactional
    public void registerExit(MovementRequestDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> {
                    log.error("Produto de ID {} não encontrado para saída de estoque", dto.getProductId());
                    return new ProductNotFoundException("Produto de ID " + dto.getProductId() + " não encontrado para movimentação de saída!");
                });

        if (product.getQuantStock() < dto.getAmount()) {
            log.warn("Tentativa de saída acima do estoque para produto '{}'. Estoque: {}, Solicitado: {}",
                    product.getName(), product.getQuantStock(), dto.getAmount());
            throw new StockMovementException("Quantidade em estoque insuficiente para o produto ID " + product.getId());
        }

        MovementStock movement = MovementStock.builder()
                .product(product)
                .typeMovement(TypeMovement.SAIDA)
                .amount(dto.getAmount())
                .dataMovement(LocalDateTime.now())
                .build();

        movementRepository.save(movement);

        product.setQuantStock(product.getQuantStock() - dto.getAmount());
        product.setLastUpdate(LocalDateTime.now());
        productRepository.save(product);

        log.info("Saída registrada: Produto '{}', Quantidade removida: {}. Estoque restante: {}",
                product.getName(), dto.getAmount(), product.getQuantStock());
    }

    public List<MovementResponseDTO> getAllMovementsToEntry() {
        log.info("Listando todas as movimentações de ENTRADA");
        return movementRepository.findByTypeMovement(TypeMovement.ENTRADA)
                .stream()
                .map(MovementMapper::toDTO)
                .toList();
    }

    public List<MovementResponseDTO> getAllMovementToExit() {
        log.info("Listando todas as movimentações de SAÍDA");
        return movementRepository.findByTypeMovement(TypeMovement.SAIDA)
                .stream()
                .map(MovementMapper::toDTO)
                .toList();
    }

    public List<MovementResponseDTO> getProductMovementHistory(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Produto de ID {} não encontrado para histórico de movimentações", id);
                    return new ProductNotFoundException("Produto de ID " + id + " não encontrado.");
                });

        log.info("Consultando histórico de movimentações do produto '{}'", product.getName());

        return movementRepository.findByProduct(product)
                .stream()
                .map(MovementMapper::toDTO)
                .toList();
    }

    public ProductStockResponseDTO getQuantityStockProducts(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Produto de ID {} não encontrado para consulta de estoque", id);
                    return new ProductNotFoundException("Produto de ID " + id + " não encontrado.");
                });

        log.info("Consultando estoque do produto '{}'. Quantidade atual: {}", product.getName(), product.getQuantStock());

        return ProductStockMapper.toDTO(product);
    }

    public Page<ProductResponseDTO> getLowStockProducts(Pageable pageable) {
        log.info("Listando produtos com estoque igual ou abaixo de {}", LOW_STOCK_LIMIT);
        return productRepository.findByQuantStockLessThanEqual(LOW_STOCK_LIMIT, pageable)
                .map(ProductMapper::toDTO);
    }
}

