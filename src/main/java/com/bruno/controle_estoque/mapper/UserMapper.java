package com.bruno.controle_estoque.mapper;

import com.bruno.controle_estoque.dto.response.UserResponseDTO;
import com.bruno.controle_estoque.model.Users;

public class UserMapper {

    public static UserResponseDTO toDTO(Users user) {
        var builder = UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole());

        return builder.build();
    }

}
