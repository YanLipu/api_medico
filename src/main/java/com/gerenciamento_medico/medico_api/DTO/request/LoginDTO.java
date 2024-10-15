package com.gerenciamento_medico.medico_api.DTO.request;

public record LoginDTO(
        String token,
        String name,
        String email,
        String role
) {
}
