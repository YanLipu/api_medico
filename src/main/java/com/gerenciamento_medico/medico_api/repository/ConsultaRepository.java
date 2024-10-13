package com.gerenciamento_medico.medico_api.repository;

import com.gerenciamento_medico.medico_api.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
}
