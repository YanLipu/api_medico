package com.gerenciamento_medico.medico_api.DTO.request;

import jakarta.validation.constraints.NotBlank;

public record ConsultationFinishDTO(

        @NotBlank(message = "Medical observation or diagnostic is required.")
        String medical_observation
) {
}
