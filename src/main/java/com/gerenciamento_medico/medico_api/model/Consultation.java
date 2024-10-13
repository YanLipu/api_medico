package com.gerenciamento_medico.medico_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private User paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private User medico;

    @Column(nullable = false)
    private LocalDateTime dataConsulta;

    @Column(nullable = false)
    private String motivoConsulta;

    @Column(nullable = false)
    private String localConsulta;

    @Enumerated(EnumType.STRING)
    private StatusConsultation status;

    private String parecerMedico;
}