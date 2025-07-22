package com.bruno.controle_estoque.controller;

import com.bruno.controle_estoque.dto.request.RegisterRequestDTO;
import com.bruno.controle_estoque.dto.request.LoginRequestDTO;
import com.bruno.controle_estoque.dto.response.AuthResponseDTO;
import com.bruno.controle_estoque.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> newRegister(
            @RequestBody @Valid RegisterRequestDTO dto
    ) {
        log.info("Requisição POST recebida na rota /api/auth/register para cadastro de novo usuário. Username: {}", dto.getUsername());

        authService.newRegister(dto);

        log.info("Usuário {} cadastrado com sucesso.", dto.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public AuthResponseDTO login(
            @RequestBody @Valid LoginRequestDTO dto
    ) {
        log.info("Requisição POST recebida na rota /api/auth/login. Tentativa de login para o usuário: {}", dto.getUsername());

        AuthResponseDTO response = authService.login(dto);

        log.info("Login realizado com sucesso para o usuário: {}", dto.getUsername());

        return response;
    }
}
