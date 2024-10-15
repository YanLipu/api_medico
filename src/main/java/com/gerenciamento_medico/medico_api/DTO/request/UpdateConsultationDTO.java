package com.gerenciamento_medico.medico_api.DTO.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateConsultationDTO(
        @NotNull(message = "Doctor ID is required.")
        Long doctor_id,

        @Future
        @NotNull(message = "Date of consultation is required.")
        LocalDateTime date_consultation,

        @NotBlank(message = "Reason of consultation is required.")
        String reason_consultation,

        @NotBlank(message = "Location of consultation is required.")
        String location_consultation
) {
}
