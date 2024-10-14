package com.gerenciamento_medico.medico_api.DTO.response;

import com.gerenciamento_medico.medico_api.model.Role;

public record RegisterUserResponseDTO (
        String name,
        String email,
        Role role
) {
}
