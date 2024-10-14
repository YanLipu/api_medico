package com.gerenciamento_medico.medico_api.DTO.response;

import com.gerenciamento_medico.medico_api.model.StatusConsultation;

import java.time.LocalDateTime;

public record ConsultationResponseDTO(
        Long id,
        LocalDateTime date_consultation,
        DoctorResponseDTO doctor,
        PatientResponseDTO patient,
        StatusConsultation status
) {
}
