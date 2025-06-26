package com.bruno.controle_estoque.controller;

import com.bruno.controle_estoque.dto.request.RegisterRequestDTO;
import com.bruno.controle_estoque.dto.request.LoginRequestDTO;
import com.bruno.controle_estoque.dto.response.AuthResponseDTO;
import com.bruno.controle_estoque.enums.Roles;
import com.bruno.controle_estoque.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> newRegister(
            @RequestBody @Valid RegisterRequestDTO dto
    ) {
        authService.newRegister(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public AuthResponseDTO login(
            @RequestBody @Valid LoginRequestDTO dto
    ) {
        return authService.login(dto);
    }

}
