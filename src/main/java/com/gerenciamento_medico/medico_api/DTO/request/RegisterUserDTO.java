package com.gerenciamento_medico.medico_api.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterUserDTO(
        @NotBlank(message = "Name is required.")
        String name,

        @Email(message = "Email must be a valid format.")
        @NotBlank(message = "Email is required.")
        String email,

        @NotBlank(message = "Password is required.")
        String password,

        @NotNull(message = "Role is required.")
        String role){
}
