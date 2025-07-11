package com.bruno.controle_estoque.dto.response;

import com.bruno.controle_estoque.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String username;
    private Roles role;
}
