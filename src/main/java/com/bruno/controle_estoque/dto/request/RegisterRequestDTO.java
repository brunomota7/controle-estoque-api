package com.bruno.controle_estoque.dto.request;

import com.bruno.controle_estoque.enums.Roles;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;

}
