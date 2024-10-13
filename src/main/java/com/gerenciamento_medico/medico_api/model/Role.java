package com.gerenciamento_medico.medico_api.model;

import lombok.Getter;

@Getter
public enum Role {
    PACIENTE("paciente"),
    ENFERMEIRO("enfermeiro"),
    MEDICO("medico"),
    ADMIN("admin");

    private String role;

    private Role(String role) {
        this.role = role;
    }
}
