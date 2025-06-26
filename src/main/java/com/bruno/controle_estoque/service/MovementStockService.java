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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
                .orElseThrow(() -> new ProductNotFoundException("Produto de ID " + dto.getProductId() + " não encontrado para movimentação de entrada!"));

        MovementStock movement = MovementStock.builder()
                .product(product)
                .typeMovement(TypeMovement.ENTRADA)
                .amount(dto.getAmount())
                .dataMovement(LocalDateTime.now())
                .build();

        movementRepository.save(movement);

        // ATUALIZA A QUANTIDADE DE ESTOQUE DO PRODUTO
        product.setQuantStock(product.getQuantStock() + dto.getAmount());
        product.setLastUpdate(LocalDateTime.now());
        productRepository.save(product);
    }

    @Transactional
    public void registerExit(MovementRequestDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Produto de ID " + dto.getProductId() + " não encontrado para movimentação de saída!"));

        if (product.getQuantStock() < dto.getAmount()) {
            throw new StockMovementException("Quantidade em estoque insuficiente para o produto ID " + product.getId() + ". Estoque atual: " + product.getQuantStock() + ", Solicitado: " + dto.getAmount());
        }

        MovementStock movementStock = MovementStock.builder()
                .product(product)
                .typeMovement(TypeMovement.SAIDA)
                .amount(dto.getAmount())
                .dataMovement(LocalDateTime.now())
                .build();

        movementRepository.save(movementStock);

        // ATUALIZA A QUANTIDADE DE ESTOQUE DO PRODUTO
        product.setQuantStock(product.getQuantStock() - dto.getAmount());
        product.setLastUpdate(LocalDateTime.now());
        productRepository.save(product);
    }

    public List<MovementResponseDTO> getAllMovementsToEntry() {
        return movementRepository.findByTypeMovement(TypeMovement.ENTRADA)
                .stream()
                .map(MovementMapper::toDTO)
                .toList();
    }

    public List<MovementResponseDTO> getAllMovementToExit() {
        return movementRepository.findByTypeMovement(TypeMovement.SAIDA)
                .stream()
                .map(MovementMapper::toDTO)
                .toList();
    }

    public List<MovementResponseDTO> getProductMovementHistory(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto de ID " + id + " não encontrado."));

        return movementRepository.findByProduct(product)
                .stream()
                .map(MovementMapper::toDTO)
                .toList();
    }

    public ProductStockResponseDTO getQuantityStockProducts(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto de ID " + id + " não encontrado."));

        return ProductStockMapper.toDTO(product);
    }


    public Page<ProductResponseDTO> getLowStockProducts(Pageable pageable) {
        return productRepository.findByQuantStockLessThanEqual(LOW_STOCK_LIMIT, pageable)
                .map(ProductMapper::toDTO);
    }

}
