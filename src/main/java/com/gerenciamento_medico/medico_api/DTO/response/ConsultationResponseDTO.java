package com.gerenciamento_medico.medico_api.DTO.response;

import java.time.LocalDateTime;

public record ConsultationResponseDTO(
        Long id,
        LocalDateTime date_consultation,
        DoctorResponseDTO doctor,
        PatientResponseDTO patient
) {
}
