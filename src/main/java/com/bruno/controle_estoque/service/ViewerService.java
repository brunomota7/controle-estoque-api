package com.bruno.controle_estoque.service;

import com.bruno.controle_estoque.dto.response.ProductResponseDTO;
import com.bruno.controle_estoque.enums.Category;
import com.bruno.controle_estoque.enums.Roles;
import com.bruno.controle_estoque.exceptions.InvalidRoleException;
import com.bruno.controle_estoque.exceptions.ProductNotFoundException;
import com.bruno.controle_estoque.mapper.ProductMapper;
import com.bruno.controle_estoque.model.Product;
import com.bruno.controle_estoque.model.Users;
import com.bruno.controle_estoque.repository.MovementRepository;
import com.bruno.controle_estoque.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
        Users authenticatedUser = authService.getUserAuthenticated();

        if (authenticatedUser.getRole() != Roles.VISUALIZADOR)
            throw new InvalidRoleException("Você não tem permissão para visualizar os produtos!");

        return productRepository.filterToSearchProducts(
                name, category, minPrice, maxPrice, pageable)
                .map(ProductMapper::toDTO);
    }

    public ProductResponseDTO detailProducts(Long id) {
        Users authenticatedUser = authService.getUserAuthenticated();

        if (authenticatedUser.getRole() != Roles.VISUALIZADOR)
            throw new InvalidRoleException("Você não tem permissão para visualizar detalhes do produto!");

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto de ID " + id + " não encontrado!"));

        return ProductMapper.toDTO(product);
    }

}
