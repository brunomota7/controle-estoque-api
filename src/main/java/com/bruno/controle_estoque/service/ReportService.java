package com.bruno.controle_estoque.service;

import com.bruno.controle_estoque.dto.response.MovementResponseDTO;
import com.bruno.controle_estoque.dto.response.StockValueResponseDTO;
import com.bruno.controle_estoque.mapper.MovementMapper;
import com.bruno.controle_estoque.model.MovementStock;
import com.bruno.controle_estoque.model.Product;
import com.bruno.controle_estoque.repository.MovementRepository;
import com.bruno.controle_estoque.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ReportService {

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<MovementResponseDTO> getMovementsByPeriod(LocalDateTime start, LocalDateTime end) {
        log.info("Iniciando geração de relatório de movimentações entre {} e {}", start, end);

        List<MovementStock> movements = movementRepository.findByDataMovementBetween(start, end);
        log.info("Movimentações encontradas: {}", movements.size());

        List<MovementResponseDTO> response = movements.stream()
                .map(MovementMapper::toDTO)
                .toList();

        log.debug("Movimentações convertidas para DTO: {}", response);
        return response;
    }

    public StockValueResponseDTO getTotalStockValue() {
        log.info("Calculando valor total do estoque.");

        List<Product> products = productRepository.findAll();
        log.info("Produtos carregados: {}", products.size());

        BigDecimal totalValue = products.stream()
                .map(p -> p.getUnitPrice().multiply(BigDecimal.valueOf(p.getQuantStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        log.info("Valor total calculado: {}", totalValue);

        return new StockValueResponseDTO(totalValue);
    }

    public List<Product> getAllProductsForStockReport() {
        log.info("Buscando todos os produtos para relatório de estoque.");

        List<Product> products = productRepository.findAll();
        log.info("Quantidade de produtos encontrados: {}", products.size());

        return products;
    }
}
