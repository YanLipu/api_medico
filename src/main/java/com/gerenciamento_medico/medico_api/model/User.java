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
public class User implements UserDetails {
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
    private List<Consultation> consultations;

    public User() {}

    public User(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == Role.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ADMIN"),
                    new SimpleGrantedAuthority("DOCTOR"),
                    new SimpleGrantedAuthority("NURSE"),
                    new SimpleGrantedAuthority("PATIENT")
            );
        } else if (this.role == Role.DOCTOR) {
            return List.of(
                    new SimpleGrantedAuthority("DOCTOR"),
                    new SimpleGrantedAuthority("NURSE"),
                    new SimpleGrantedAuthority("PATIENT")
            );
        } else if (this.role == Role.NURSE) {
            return List.of(
                    new SimpleGrantedAuthority("NURSE"),
                    new SimpleGrantedAuthority("PATIENT")
            );
        } else if(this.role == Role.PATIENT) {
            return List.of(
                    new SimpleGrantedAuthority("PATIENT")
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