package com.bruno.controle_estoque.dto.request;

import com.bruno.controle_estoque.enums.Roles;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequestDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private Roles role;
}
