package com.bruno.controle_estoque.service;

import com.bruno.controle_estoque.dto.request.RegisterRequestDTO;
import com.bruno.controle_estoque.dto.request.LoginRequestDTO;
import com.bruno.controle_estoque.dto.response.AuthResponseDTO;
import com.bruno.controle_estoque.enums.Roles;
import com.bruno.controle_estoque.exceptions.ExistingUserException;
import com.bruno.controle_estoque.exceptions.InvalidPasswordException;
import com.bruno.controle_estoque.model.Users;
import com.bruno.controle_estoque.repository.UsersRepository;
import com.bruno.controle_estoque.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void newRegister(RegisterRequestDTO dto) {
        if (usersRepository.findByUsername(dto.getUsername()).isPresent())
            throw new ExistingUserException("Já existe um usuário cadastrado com esse username. Tente novamente com um novo email.");

        Users user = Users.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Roles.VISUALIZADOR)
                .build();

        usersRepository.save(user);
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {
        Users user = usersRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado. Tente novamente."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
            throw new InvalidPasswordException("Senha inválida. Tente novamente.");

        String token = jwtUtil.generateToken(user);

        return new AuthResponseDTO(token, user.getUsername(), user.getRole());
    }

    public Users getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado. Tente novamente."));
    }

}
