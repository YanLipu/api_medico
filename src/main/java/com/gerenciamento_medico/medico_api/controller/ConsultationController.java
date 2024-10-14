package com.gerenciamento_medico.medico_api.controller;

import com.gerenciamento_medico.medico_api.DTO.request.ConsultationDTO;
import com.gerenciamento_medico.medico_api.DTO.request.ConsultationFinishDTO;
import com.gerenciamento_medico.medico_api.DTO.response.ConsultationResponseDTO;
import com.gerenciamento_medico.medico_api.model.Consultation;
import com.gerenciamento_medico.medico_api.model.User;
import com.gerenciamento_medico.medico_api.service.ConsultationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultation")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @PostMapping
    public ResponseEntity<ConsultationResponseDTO> requestConsultation(@RequestBody @Valid ConsultationDTO consultation) {
        ConsultationResponseDTO newConsultation = consultationService.requestConsultation(consultation);
        return ResponseEntity.ok(newConsultation);
    }

    @GetMapping
    public List<Consultation> listConsultations() {
        return consultationService.listConsultations();
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<ConsultationResponseDTO> approveConsultation(@PathVariable Long id) {
        ConsultationResponseDTO consultationApproved = consultationService.approveConsultation(id);
        return consultationApproved != null ? ResponseEntity.ok(consultationApproved) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/finish")
    public ResponseEntity<ConsultationResponseDTO> finishConsultation(
            @PathVariable Long id,
            @RequestBody @Valid ConsultationFinishDTO medicalObservation,
            Authentication authentication
    ) {
        User authenticatedUser = (User) authentication.getPrincipal();
        Long doctorId = authenticatedUser.getId();

        ConsultationResponseDTO consultationFinalized = consultationService.finishConsultation(id, doctorId, medicalObservation);
        return consultationFinalized != null ? ResponseEntity.ok(consultationFinalized) : ResponseEntity.notFound().build();
    }
}
