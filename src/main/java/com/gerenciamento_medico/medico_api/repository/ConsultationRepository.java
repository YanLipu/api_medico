package com.gerenciamento_medico.medico_api.repository;

import com.gerenciamento_medico.medico_api.model.Consultation;
import com.gerenciamento_medico.medico_api.model.StatusConsultation;
import com.gerenciamento_medico.medico_api.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    @Query("SELECT c FROM Consultation c WHERE c.doctor.id = :doctorId " +
            "AND c.date_consultation BETWEEN :startTime AND :endTime")
    List<Consultation> findConflictingConsultations(
            @Param("doctorId") Long doctorId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("SELECT c FROM Consultation c")
    Page<Consultation> findAll(Pageable pageable);

    @Query("SELECT c FROM Consultation c WHERE c.doctor.id = :doctorId")
    Page<Consultation> findByDoctorId(@Param("doctorId") Long doctorId, Pageable pageable);

    @Modifying
    @Transactional  // Required to wrap the update operation in a transaction
    @Query("UPDATE Consultation c SET c.status = :status, c.date_consultation = :dateConsultation WHERE c.id = :consultationId")
    int updateConsultationById(@Param("consultationId") Long consultationId,
                               @Param("status") StatusConsultation status,
                               @Param("dateConsultation") LocalDateTime dateConsultation);
}
