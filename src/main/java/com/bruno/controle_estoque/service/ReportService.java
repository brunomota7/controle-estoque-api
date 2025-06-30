package com.bruno.controle_estoque.service;

import com.bruno.controle_estoque.dto.response.MovementResponseDTO;
import com.bruno.controle_estoque.dto.response.StockValueResponseDTO;
import com.bruno.controle_estoque.mapper.MovementMapper;
import com.bruno.controle_estoque.model.MovementStock;
import com.bruno.controle_estoque.model.Product;
import com.bruno.controle_estoque.repository.MovementRepository;
import com.bruno.controle_estoque.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    @Autowired private MovementRepository movementRepository;

    @Autowired private ProductRepository productRepository;

    public List<MovementResponseDTO> getMovementsByPeriod(LocalDateTime start, LocalDateTime end) {
        List<MovementStock> movements = movementRepository.findByDataMovementBetween(start, end);
        return movements.stream().map(MovementMapper::toDTO).toList();
    }

    public StockValueResponseDTO getTotalStockValue() {
        List<Product> products = productRepository.findAll();

        BigDecimal totalValue = products.stream()
                .map(p -> p.getUnitPrice().multiply(BigDecimal.valueOf(p.getQuantStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new StockValueResponseDTO(totalValue);
    }

    public List<Product> getAllProductsForStockReport() {
        return productRepository.findAll();
    }

}
