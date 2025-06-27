package com.bruno.controle_estoque.controller;

import com.bruno.controle_estoque.dto.request.PromoteRequestDTO;
import com.bruno.controle_estoque.dto.response.UserResponseDTO;
import com.bruno.controle_estoque.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers()
            throws AccessDeniedException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(adminService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable Long id
    ) throws AccessDeniedException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(adminService.getUserById(id));
    }

    @PutMapping("/promote")
    public ResponseEntity<?> promoteUser(
            @RequestBody @Valid PromoteRequestDTO dto
    ) throws AccessDeniedException {
        adminService.promoteUser(dto);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable Long id
    ) throws AccessDeniedException {
        adminService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

}
