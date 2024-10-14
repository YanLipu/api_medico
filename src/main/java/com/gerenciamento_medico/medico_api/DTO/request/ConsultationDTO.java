package com.gerenciamento_medico.medico_api.DTO.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record ConsultationDTO (
        @NotNull(message = "Patient id is required.")
        Long patient_id,

        @NotNull(message = "Doctor id is required.")
        Long doctor_id,

        @Future
        @NotNull(message = "Consultation date is required.")
        LocalDateTime date_consultation,

        @NotBlank(message = "Consultation location is required.")
        String location_consultation,

        @NotBlank(message = "Consultation reason is required.")
        String reason_consultation
        ) {
}
