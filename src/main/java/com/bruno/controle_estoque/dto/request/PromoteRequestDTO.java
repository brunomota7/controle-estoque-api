package com.bruno.controle_estoque.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PromoteRequestDTO {

    @NotNull
    private String username;

    @NotNull
    private String newRole;
}
