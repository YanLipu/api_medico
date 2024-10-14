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
    @JoinColumn(name = "patient_id")
    private User patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @Column(nullable = false)
    private LocalDateTime date_consultation;

    @Column(nullable = false)
    private String reason_consultation;

    @Column(nullable = false)
    private String location_consultation;

    @Enumerated(EnumType.STRING)
    private StatusConsultation status;

    private String medical_observation;
}