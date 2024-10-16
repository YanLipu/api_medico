package com.gerenciamento_medico.medico_api.repository;

import com.gerenciamento_medico.medico_api.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByEmail(String email);

    List<User> findByNameStartingWithIgnoreCase(String name, Pageable pageable);
}