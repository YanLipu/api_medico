package com.gerenciamento_medico.medico_api.controller;

import com.gerenciamento_medico.medico_api.DTO.request.ConsultationDTO;
import com.gerenciamento_medico.medico_api.DTO.request.ConsultationFinishDTO;
import com.gerenciamento_medico.medico_api.DTO.response.ConsultationResponseDTO;
import com.gerenciamento_medico.medico_api.model.Consultation;
import com.gerenciamento_medico.medico_api.model.Role;
import com.gerenciamento_medico.medico_api.model.User;
import com.gerenciamento_medico.medico_api.service.ConsultationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/schedule")
    public ResponseEntity<Page<ConsultationResponseDTO>> listConsultations(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date_consultation") String[] sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Pagination.parseSortParams(sort)));
        User authenticatedUser = (User) authentication.getPrincipal();

        Page<ConsultationResponseDTO> schedules;
        if (authenticatedUser.getRole() == Role.DOCTOR) {
            Long doctorId = authenticatedUser.getId();
            return ResponseEntity.ok(consultationService.listAllConsultationsByDoctorId(pageable, doctorId));
        }
        return ResponseEntity.ok(consultationService.listAllConsultations(pageable));
//        return ResponseEntity.ok()
//        return consultationService.listConsultations();
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
