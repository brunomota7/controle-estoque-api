package com.bruno.controle_estoque.controller;

import com.bruno.controle_estoque.dto.request.MovementRequestDTO;
import com.bruno.controle_estoque.dto.response.MovementResponseDTO;
import com.bruno.controle_estoque.dto.response.ProductResponseDTO;
import com.bruno.controle_estoque.dto.response.ProductStockResponseDTO;
import com.bruno.controle_estoque.service.MovementStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@PreAuthorize("hasRole('GERENTE_ESTOQUE') or hasRole('ADMIN')")
public class MovementStockController {

    @Autowired
    private MovementStockService movementStockService;

    @PostMapping("/entry")
    public ResponseEntity<?> registerEntry(
            @RequestBody MovementRequestDTO dto
    ) {
        movementStockService.registerEntry(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/exit")
    public ResponseEntity<?> registerExit(
            @RequestBody MovementRequestDTO dto
    ) {
        movementStockService.registerExit(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/entry")
    public ResponseEntity<List<MovementResponseDTO>> getAllMovementsToEntry() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(movementStockService.getAllMovementsToEntry());
    }

    @GetMapping("/exit")
    public ResponseEntity<List<MovementResponseDTO>> getAllMovementToExit() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(movementStockService.getAllMovementToExit());
    }

    @GetMapping("/product/{productId}/history")
    public ResponseEntity<List<MovementResponseDTO>> getProductMovementHistory(
            @PathVariable Long productId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(movementStockService.getProductMovementHistory(productId));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductStockResponseDTO> getQuantityStockProducts(
            @PathVariable Long productId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(movementStockService.getQuantityStockProducts(productId));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<Page<ProductResponseDTO>> getLowStockProducts(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(movementStockService.getLowStockProducts(pageable));
    }

}
