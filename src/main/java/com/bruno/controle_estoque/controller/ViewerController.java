package com.bruno.controle_estoque.controller;

import com.bruno.controle_estoque.dto.response.MovementResponseDTO;
import com.bruno.controle_estoque.dto.response.ProductResponseDTO;
import com.bruno.controle_estoque.enums.Category;
import com.bruno.controle_estoque.enums.Roles;
import com.bruno.controle_estoque.exceptions.InvalidRoleException;
import com.bruno.controle_estoque.model.Users;
import com.bruno.controle_estoque.service.AuthService;
import com.bruno.controle_estoque.service.MovementStockService;
import com.bruno.controle_estoque.service.ProductService;
import com.bruno.controle_estoque.service.ViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/viewer")
@PreAuthorize("hasRole('VISUALIZADOR')")
public class ViewerController {

    @Autowired private ViewerService viewerService;

    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @PageableDefault(page = 0, size = 5, sort = "name") Pageable pageable
    ) {
        Page<ProductResponseDTO> productsPage = viewerService.viewProducts(
                name, category, minPrice, maxPrice, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(productsPage);
    }

    @GetMapping("/products-detail")
    public ResponseEntity<ProductResponseDTO> detailProducts(
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(viewerService.detailProducts(id));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<Page<ProductResponseDTO>> getAllProductsLowStock(
            @PageableDefault(page = 0, size = 10, sort = "name") Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(viewerService.getAllProductsLowStock(pageable));
    }

    @GetMapping("/stock-entry")
    public ResponseEntity<List<MovementResponseDTO>> getAllMovementsEntry() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(viewerService.getAllMovementsEntry());
    }

    @GetMapping("/stock-exit")
    public ResponseEntity<List<MovementResponseDTO>> getAllMovementsExit() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(viewerService.getAllMovementsExit());
    }

    @GetMapping("/movement-history/{productId}")
    public ResponseEntity<List<MovementResponseDTO>> getProductMovementHistory(
            @PathVariable Long productId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(viewerService.getProductMovementHistory(productId));
    }

}
