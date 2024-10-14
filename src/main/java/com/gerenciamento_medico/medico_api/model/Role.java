package com.gerenciamento_medico.medico_api.model;

import lombok.Getter;

@Getter
public enum Role {
    PATIENT("patient"),
    NURSE("nurse"),
    DOCTOR("doctor"),
    ADMIN("admin");

    private final String role;

    private Role(String role) {
        this.role = role;
    }
}
