package com.bruno.controle_estoque.service;

import com.bruno.controle_estoque.dto.request.PromoteRequestDTO;
import com.bruno.controle_estoque.dto.response.UserResponseDTO;
import com.bruno.controle_estoque.enums.Roles;
import com.bruno.controle_estoque.exceptions.InvalidRoleException;
import com.bruno.controle_estoque.mapper.UserMapper;
import com.bruno.controle_estoque.model.Users;
import com.bruno.controle_estoque.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Slf4j
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

        log.info("Admin '{}' fez uma requesição para buscar todos os usuários.", authenticatedUser.getUsername());

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

        log.info("Admin '{}' fez uma requesição para buscar por usuário. Buscou o usuário: '{}'", authenticatedUser.getUsername(), user.getUsername());

        return UserMapper.toDTO(user);
    }

    public void promoteUser(PromoteRequestDTO dto) throws AccessDeniedException {

        Users authenticatedUser = authService.getUserAuthenticated();

        if (authenticatedUser.getRole() != Roles.ADMIN) {

            log.error("Usuário '{}', tentou promover '{}', para outros cargo, mas não tem permissão.", authenticatedUser.getUsername(), dto.getUsername());
            throw new AccessDeniedException("Apenas administradores podem promover usuários.");

        }

        Users user = usersRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado. Tente novamente!"));

        Roles newRole;
        try {
            newRole = Roles.valueOf(dto.getNewRole().toUpperCase());
        } catch (IllegalArgumentException ex) {

            log.error("Admin '{}' tentou usar um role inexistente.", authenticatedUser.getUsername(), dto.getUsername());
            throw new InvalidRoleException("Role inválida: " + dto.getNewRole());

        }

        if (user.getRole() == Roles.ADMIN && newRole != Roles.ADMIN) {
            log.error("Admin '{}', tentou rebaixar o usuário admin '{}'", authenticatedUser.getUsername(), dto.getUsername());
            throw new InvalidRoleException("Não é permitido rebaixar um ADMIN.");
        }

        if (user.getRole() == newRole) {
            log.error("Admin '{}', tentou promover o usuário '{}'", authenticatedUser.getUsername(), dto.getUsername());
            throw new InvalidRoleException("Usuário já possui o cargo " + newRole + ".");
        }

        log.info("Admin '{}', promoveu o usuário '{}'.", authenticatedUser.getUsername(), user.getUsername());

        user.setRole(newRole);
        usersRepository.save(user);
    }

    public void deleteUser(Long id) throws AccessDeniedException {
        Users authenticatedUser = authService.getUserAuthenticated();

        if (authenticatedUser.getRole() != Roles.ADMIN) {
            log.error("'{}' tentou excluír um usuário, mas não tem autorização.", authenticatedUser.getUsername());
            throw new AccessDeniedException("Apenas administradores podem excluír usuários.");
        }

        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado. Tente novamente!"));

        log.info("Admin '{}', excluiu o usuário de ID '{}', nome: '{}'", authenticatedUser.getUsername(), id, user.getUsername());

        usersRepository.delete(user);
    }

}
