package com.gerenciamento_medico.medico_api.repository;

import com.gerenciamento_medico.medico_api.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}
