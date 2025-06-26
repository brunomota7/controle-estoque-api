package com.bruno.controle_estoque.service;

import com.bruno.controle_estoque.dto.request.ProductRequestDTO;
import com.bruno.controle_estoque.dto.response.ProductResponseDTO;
import com.bruno.controle_estoque.enums.Category;
import com.bruno.controle_estoque.exceptions.ProductNotFoundException;
import com.bruno.controle_estoque.mapper.ProductMapper;
import com.bruno.controle_estoque.model.Product;
import com.bruno.controle_estoque.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private static final int LOW_STOCK_LIMIT = 10;

    @Transactional
    public void addNewProduct(ProductRequestDTO dto) {
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
    }

    public Page<ProductResponseDTO> searchProducts(
            String name,
            Category category,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    ) {
        return productRepository.filterToSearchProducts(
                name, category, minPrice, maxPrice, pageable)
                .map(ProductMapper::toDTO);
    }

    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto de ID " + id + " não encontrado!"));

        return ProductMapper.toDTO(product);
    }

    public Page<ProductResponseDTO> getLowStockProducts(Pageable pageable) {
        return productRepository.findByQuantStockLessThanEqual(LOW_STOCK_LIMIT, pageable)
                .map(ProductMapper::toDTO);
    }

    public void updateProducts(Long id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto de ID " + id + " não encontrado!"));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setUnitPrice(dto.getUnitPrice());
        product.setQuantStock(dto.getQuantStock());
        product.setSku(dto.getSku());
        product.setLastUpdate(LocalDateTime.now());

        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto de ID " + id + " não encontrado!"));

        productRepository.delete(product);
    }

}
