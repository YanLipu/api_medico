package com.gerenciamento_medico.medico_api.DTO.response;

import java.util.List;

public record UserSearchResponseDTO(
        List<UserSearchItem> users,
        int totalResults
) {
    public record UserSearchItem(
            Long id,
            String name,
            String email
    ) {}
}