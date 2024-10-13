package com.gerenciamento_medico.medico_api.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ConsultationDTO {
    // Getters e Setters
    private Long pacienteId;
    private Long medicoId;
    private LocalDateTime dataConsulta;

}
