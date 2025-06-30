package com.bruno.controle_estoque.service;

import com.bruno.controle_estoque.dto.request.PromoteRequestDTO;
import com.bruno.controle_estoque.dto.response.UserResponseDTO;
import com.bruno.controle_estoque.enums.Roles;
import com.bruno.controle_estoque.exceptions.InvalidRoleException;
import com.bruno.controle_estoque.mapper.UserMapper;
import com.bruno.controle_estoque.model.Users;
import com.bruno.controle_estoque.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthService authService;

    public List<UserResponseDTO> getAllUsers() throws AccessDeniedException {

        Users authenticatedUser = authService.getUserAuthenticated();

        if (authenticatedUser.getRole() != Roles.ADMIN) {
            throw new AccessDeniedException("Apenas administradores podem listar usuários.");
        }

        return usersRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    public UserResponseDTO getUserById(Long id) throws AccessDeniedException {
        Users authenticatedUser = authService.getUserAuthenticated();

        if (authenticatedUser.getRole() != Roles.ADMIN) {
            throw new AccessDeniedException("Apenas administradores podem buscar por usuários.");
        }

        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário de ID " + id + " não encontrado. Tente novamente!"));

        return UserMapper.toDTO(user);
    }

    public void promoteUser(PromoteRequestDTO dto) throws AccessDeniedException {

        Users authenticatedUser = authService.getUserAuthenticated();

        if (authenticatedUser.getRole() != Roles.ADMIN) {
            throw new AccessDeniedException("Apenas administradores podem promover usuários.");
        }

        Users user = usersRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado. Tente novamente!"));

        Roles newRole;
        try {
            newRole = Roles.valueOf(dto.getNewRole().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidRoleException("Role inválida: " + dto.getNewRole());
        }

        if (user.getRole() == Roles.ADMIN && newRole != Roles.ADMIN)
            throw new InvalidRoleException("Não é permitido rebaixar um ADMIN.");

        if (user.getRole() == newRole)
            throw new InvalidRoleException("Usuário já possui o cargo " + newRole + ".");

        user.setRole(newRole);
        usersRepository.save(user);
    }

    public void deleteUser(Long id) throws AccessDeniedException {
        Users authenticatedUser = authService.getUserAuthenticated();

        if (authenticatedUser.getRole() != Roles.ADMIN) {
            throw new AccessDeniedException("Apenas administradores podem excluír usuários.");
        }

        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado. Tente novamente!"));

        usersRepository.delete(user);
    }

}
