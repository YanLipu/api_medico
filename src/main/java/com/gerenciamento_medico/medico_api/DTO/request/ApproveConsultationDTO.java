package com.gerenciamento_medico.medico_api.DTO.request;

import jakarta.validation.constraints.NotNull;

public record ApproveConsultationDTO(
        @NotNull(message = "Consultation ID is required.")
        Long consultation_id
) {
}
