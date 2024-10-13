package com.gerenciamento_medico.medico_api.repository;

import com.gerenciamento_medico.medico_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}