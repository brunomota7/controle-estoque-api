package com.bruno.controle_estoque.controller;

import com.bruno.controle_estoque.dto.request.MovementRequestDTO;
import com.bruno.controle_estoque.dto.response.MovementResponseDTO;
import com.bruno.controle_estoque.service.MovementStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class MovementStockController {

    @Autowired
    private MovementStockService movementStockService;

    @PostMapping("/entrada")
    public ResponseEntity<?> registerEntry(
            @RequestBody MovementRequestDTO dto
    ) {
        movementStockService.registerEntry(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/saida")
    public ResponseEntity<?> registerExit(
            @RequestBody MovementRequestDTO dto
    ) {
        movementStockService.registerExit(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/entrada")
    public ResponseEntity<List<MovementResponseDTO>> getAllMovementsToEntry() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(movementStockService.getAllMovementsToEntry());
    }

    @GetMapping("/saida")
    public ResponseEntity<List<MovementResponseDTO>> getAllMovementToExit() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(movementStockService.getAllMovementToExit());
    }

    @GetMapping("/produto/{productId}/historico")
    public ResponseEntity<List<MovementResponseDTO>> getProductMovementHistory(
            @PathVariable Long productId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(movementStockService.getProductMovementHistory(productId));
    }

}
