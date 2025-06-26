package com.bruno.controle_estoque.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;

}
