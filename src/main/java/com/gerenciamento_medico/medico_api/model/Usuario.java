package com.gerenciamento_medico.medico_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Entity
public class Usuario implements UserDetails {
    // Getters e Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "paciente")
    private List<Consulta> consultas;

    public Usuario() {}

    public Usuario(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == Role.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ADMIN"),
                    new SimpleGrantedAuthority("MEDICO"),
                    new SimpleGrantedAuthority("ENFERMEIRO"),
                    new SimpleGrantedAuthority("PACIENTE")
            );
        } else if (this.role == Role.MEDICO) {
            return List.of(
                    new SimpleGrantedAuthority("MEDICO"),
                    new SimpleGrantedAuthority("ENFERMEIRO"),
                    new SimpleGrantedAuthority("PACIENTE")
            );
        } else if (this.role == Role.ENFERMEIRO) {
            return List.of(
                    new SimpleGrantedAuthority("ENFERMEIRO"),
                    new SimpleGrantedAuthority("PACIENTE")
            );
        } else if(this.role == Role.PACIENTE) {
            return List.of(
                    new SimpleGrantedAuthority("PACIENTE")
            );
        } else {
            return List.of();
        }
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}