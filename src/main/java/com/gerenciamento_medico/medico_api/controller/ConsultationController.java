package com.gerenciamento_medico.medico_api.controller;

import com.gerenciamento_medico.medico_api.DTO.request.ConsultationDTO;
import com.gerenciamento_medico.medico_api.DTO.request.ConsultationFinishDTO;
import com.gerenciamento_medico.medico_api.DTO.request.UpdateConsultationDTO;
import com.gerenciamento_medico.medico_api.DTO.response.ConsultationResponseDTO;
import com.gerenciamento_medico.medico_api.model.Consultation;
import com.gerenciamento_medico.medico_api.model.Role;
import com.gerenciamento_medico.medico_api.model.User;
import com.gerenciamento_medico.medico_api.service.ConsultationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1/consultation")
@Tag(name = "Consultation", description = "Consultation management APIs")
@SecurityRequirement(name = "bearerAuth")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @Operation(summary = "Request a new consultation", description = "Creates a new consultation request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultation requested successfully",
                    content = @Content(schema = @Schema(implementation = ConsultationResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ResponseEntity<ConsultationResponseDTO> requestConsultation(@RequestBody @Valid ConsultationDTO consultation) {
        ConsultationResponseDTO newConsultation = consultationService.requestConsultation(consultation);
        return ResponseEntity.ok(newConsultation);
    }

    @Operation(summary = "List consultations", description = "Get a paginated list of consultations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of consultations",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    @GetMapping("/schedule")
    public ResponseEntity<Page<ConsultationResponseDTO>> listConsultations(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date_consultation") String[] sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Pagination.parseSortParams(sort)));
        User authenticatedUser = (User) authentication.getPrincipal();

        if (authenticatedUser.getRole() == Role.DOCTOR) {
            Long doctorId = authenticatedUser.getId();
            return ResponseEntity.ok(consultationService.listAllConsultationsByDoctorId(pageable, doctorId));
        }
        return ResponseEntity.ok(consultationService.listAllConsultations(pageable));
    }

    @Operation(summary = "Approve a consultation", description = "Approves a pending consultation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultation approved successfully",
                    content = @Content(schema = @Schema(implementation = ConsultationResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Consultation not found")
    })
    @PutMapping("/{id}/approve")
    public ResponseEntity<ConsultationResponseDTO> approveConsultation(@PathVariable Long id) {
        ConsultationResponseDTO consultationApproved = consultationService.approveConsultation(id);
        return consultationApproved != null ? ResponseEntity.ok(consultationApproved) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Finish a consultation", description = "Marks a consultation as finished and adds medical observations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultation finished successfully",
                    content = @Content(schema = @Schema(implementation = ConsultationResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Consultation not found")
    })
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

    @Operation(summary = "Request a change in consultation", description = "Requests a change for an existing consultation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultation change requested successfully",
                    content = @Content(schema = @Schema(implementation = ConsultationResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Consultation not found")
    })
    @PutMapping("/{id}/request-change")
    public ResponseEntity<ConsultationResponseDTO> requestConsultationChange(
            @PathVariable Long id,
            @RequestBody @Valid UpdateConsultationDTO updateConsultationRequest,
            Authentication authentication
            ){
        User authenticatedUser = (User) authentication.getPrincipal();

        ConsultationResponseDTO consultationUpdated = consultationService.requestConsultationUpdate(
                id,
                authenticatedUser,
                updateConsultationRequest
        );

        return consultationUpdated != null ? ResponseEntity.ok(consultationUpdated) : ResponseEntity.notFound().build();
    }
}
