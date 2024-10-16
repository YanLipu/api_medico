package com.gerenciamento_medico.medico_api.service;

import com.gerenciamento_medico.medico_api.DTO.request.ConsultationDTO;
import com.gerenciamento_medico.medico_api.DTO.request.ConsultationFinishDTO;
import com.gerenciamento_medico.medico_api.DTO.request.UpdateConsultationDTO;
import com.gerenciamento_medico.medico_api.DTO.response.ConsultationResponseDTO;
import com.gerenciamento_medico.medico_api.DTO.response.DoctorResponseDTO;
import com.gerenciamento_medico.medico_api.DTO.response.PatientResponseDTO;
import com.gerenciamento_medico.medico_api.exceptions.UnauthorizedAccessException;
import com.gerenciamento_medico.medico_api.model.Consultation;
import com.gerenciamento_medico.medico_api.model.Role;
import com.gerenciamento_medico.medico_api.model.StatusConsultation;
import com.gerenciamento_medico.medico_api.model.User;
import com.gerenciamento_medico.medico_api.repository.ConsultationRepository;
import jakarta.validation.ValidationException;
import com.gerenciamento_medico.medico_api.exceptions.GlobalExceptionHandler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.dnd.InvalidDnDOperationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private UserService userService;

    public ConsultationResponseDTO requestConsultation(ConsultationDTO consultation) {
        User doctor = validateAndGetDoctor(consultation.doctor_id());
        User patient = validateAndGetPatient(consultation.patient_id());

        validateConsultationTime(doctor, consultation.date_consultation());

        Consultation consultationRequested = createConsultationFromDTO(consultation, doctor, patient);
        Consultation savedConsultation = consultationRepository.save(consultationRequested);

        return createConsultationResponseDTO(savedConsultation);
    }

    public Page<ConsultationResponseDTO> listAllConsultations(Pageable pageable) {
        Page<Consultation> allConsultations = consultationRepository.findAll(pageable);
        return allConsultations.map(this::createConsultationResponseDTO);
    }

    public Page<ConsultationResponseDTO> listAllConsultationsByDoctorId(Pageable pageable, Long doctorId) {
        Page<Consultation> allConsultations = consultationRepository.findByDoctorId(doctorId, pageable);
        return allConsultations.map(this::createConsultationResponseDTO);
    }

    public Page<ConsultationResponseDTO> listAllConsultationsByPatientId(Pageable pageable, Long patientId) {
        Page<Consultation> allConsultationsByPatient = consultationRepository.findByPatientId(patientId, pageable);
        return allConsultationsByPatient.map(this::createConsultationResponseDTO);
    }

    public ConsultationResponseDTO approveConsultation(Long id) {
        Optional<Consultation> consultationOptional = consultationRepository.findById(id);
        if(consultationOptional.isEmpty()) {
            throw new IllegalArgumentException("Consultation not found.");
        }

        if(consultationOptional.get().getStatus() != StatusConsultation.PENDING) {
            throw new IllegalArgumentException("Consultation is not pending");
        }

        Consultation consultation = consultationOptional.get();
        consultation.setStatus(StatusConsultation.APPROVED);

        Consultation savedConsultation = consultationRepository.save(consultation);

        DoctorResponseDTO savedDoctor = new DoctorResponseDTO(savedConsultation.getDoctor().getId(), savedConsultation.getDoctor().getName());
        PatientResponseDTO savedPatient = new PatientResponseDTO(savedConsultation.getPatient().getId(), savedConsultation.getPatient().getName());
        return new ConsultationResponseDTO(
                savedConsultation.getId(),
                savedConsultation.getDate_consultation(),
                savedDoctor,
                savedPatient,
                savedConsultation.getStatus(),
                savedConsultation.getMedical_observation(),
                savedConsultation.getLocation_consultation()
        );
    }

    public Consultation rejectConsultation(Long id) {
        Optional<Consultation> consultaOptional = consultationRepository.findById(id);
        if (consultaOptional.isPresent()) {
            Consultation consultation = consultaOptional.get();
            consultation.setStatus(StatusConsultation.REJECTED);
            return consultationRepository.save(consultation);
        }
        return null;
    }

    public ConsultationResponseDTO finishConsultation(Long consultationId, Long doctorId, ConsultationFinishDTO finishDTO) {
        Optional<Consultation> consultationOptional = consultationRepository.findById(consultationId);
        if (consultationOptional.isEmpty()) {
            throw new IllegalArgumentException("Consultation not found with id: " + consultationId);
        }

        Consultation consultation = consultationOptional.get();

        validateDoctorAccess(consultation, doctorId);

        if (consultation.getStatus() != StatusConsultation.APPROVED) {
            throw new UnauthorizedAccessException("Only approved consultations can be finished.");
        }

        consultation.setStatus(StatusConsultation.COMPLETED);
        consultation.setMedical_observation(finishDTO.medical_observation());

        Consultation savedConsultation = consultationRepository.save(consultation);

        return createConsultationResponseDTO(savedConsultation);
    }

    public ConsultationResponseDTO requestConsultationUpdate(
            Long consultationId,
            User authenticatedUser,
            UpdateConsultationDTO consultation
    ) {
        if (authenticatedUser.getRole() != Role.PATIENT) {
            throw new UnauthorizedAccessException("Only patients can request consultation changes or updates");
        }

        Optional<Consultation> consultationOptional = consultationRepository.findById(consultationId);

        if (consultationOptional.isEmpty()) {
            throw new IllegalArgumentException("Consultation not found.");
        }

        Consultation existingConsultation = consultationOptional.get();

        if (existingConsultation.getStatus() == StatusConsultation.COMPLETED) {
            throw new IllegalArgumentException("Consultation is already completed");
        }

        if (authenticatedUser.getRole() == Role.PATIENT && !existingConsultation.getPatient().getId().equals(authenticatedUser.getId())) {
            throw new UnauthorizedAccessException("You are not allowed to update this consultation");
        }

        User doctor = validateAndGetDoctor(consultation.doctor_id());

        validateConsultationTime(doctor, consultation.date_consultation());

        int rowsUpdated = consultationRepository.updateConsultationById(
                consultationId,
                StatusConsultation.PENDING,
                consultation.date_consultation()
        );

        if (rowsUpdated == 0) {
            throw new IllegalArgumentException("Failed to update consultation.");
        }

        Consultation updatedConsultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new IllegalArgumentException("Consultation not found after update."));

        return createConsultationResponseDTO(updatedConsultation);
    }

    public Page<ConsultationResponseDTO> listConsultationsByStatus(Pageable pageable, StatusConsultation status) {
        Page<Consultation> consultations = consultationRepository.findByStatus(status, pageable);
        return consultations.map(this::createConsultationResponseDTO);
    }

    public Page<ConsultationResponseDTO> listConsultationsByDoctorIdAndStatus(Pageable pageable, Long doctorId, StatusConsultation status) {
        Page<Consultation> consultations = consultationRepository.findByDoctorIdAndStatus(doctorId, status, pageable);
        return consultations.map(this::createConsultationResponseDTO);
    }

    public Page<ConsultationResponseDTO> listConsultationsByPatientIdAndStatus(Pageable pageable, Long patientId, StatusConsultation status) {
        Page<Consultation> consultations = consultationRepository.findByPatientIdAndStatus(patientId, status, pageable);
        return consultations.map(this::createConsultationResponseDTO);
    }

    private User validateAndGetDoctor(Long doctorId) {
        Optional<User> doctor = userService.findDoctorById(doctorId);
        if (doctor.isEmpty()) {
            throw new IllegalArgumentException("Doctor not found");
        }
        if (doctor.get().getRole() != Role.DOCTOR) {
            throw new IllegalArgumentException("The user " + doctorId + " is not a doctor.");
        }
        return doctor.get();
    }

    private User validateAndGetPatient(Long patientId) {
        Optional<User> patient = userService.findUserById(patientId);
        if (patient.isEmpty()) {
            throw new IllegalArgumentException("Invalid patient");
        }
        if (patient.get().getRole() != Role.PATIENT) {
            throw new IllegalArgumentException("The user " + patientId + " is not a patient.");
        }
        return patient.get();
    }

    private void validateDoctorAccess(Consultation consultation, Long doctorId) {
        if (!consultation.getDoctor().getId().equals(doctorId)) {
            throw new UnauthorizedAccessException("You do not have permission to finish this consultation.");
        }
    }

    private void validateConsultationTime(User doctor, LocalDateTime consultationTime) {
        LocalDateTime oneHourBefore = consultationTime.minusHours(1);
        LocalDateTime oneHourAfter = consultationTime.plusHours(1);

        List<Consultation> conflictingConsultations = consultationRepository.findConflictingConsultations(
                doctor.getId(), oneHourBefore, oneHourAfter);

        if (!conflictingConsultations.isEmpty()) {
            throw new IllegalArgumentException("There is already a consultation scheduled within one hour of the requested time.");
        }
    }

    private Consultation createConsultationFromDTO(ConsultationDTO consultationDTO, User doctor, User patient) {
        Consultation consultation = new Consultation();
        consultation.setStatus(StatusConsultation.PENDING);
        consultation.setDoctor(doctor);
        consultation.setPatient(patient);
        consultation.setDate_consultation(consultationDTO.date_consultation());
        consultation.setLocation_consultation(consultationDTO.location_consultation());
        consultation.setReason_consultation(consultationDTO.reason_consultation());
        return consultation;
    }

    private ConsultationResponseDTO createConsultationResponseDTO(Consultation consultation) {
        DoctorResponseDTO doctorDTO = new DoctorResponseDTO(
                consultation.getDoctor().getId(),
                consultation.getDoctor().getName()
        );
        PatientResponseDTO patientDTO = new PatientResponseDTO(
                consultation.getPatient().getId(),
                consultation.getPatient().getName()
        );

        return new ConsultationResponseDTO(
                consultation.getId(),
                consultation.getDate_consultation(),
                doctorDTO,
                patientDTO,
                consultation.getStatus(),
                consultation.getMedical_observation(),
                consultation.getLocation_consultation()
        );
    }

    private Consultation updateConsultationFromDTO(UpdateConsultationDTO consultationDTO, User doctor, User patient) {
        Consultation consultation = new Consultation();
        consultation.setStatus(StatusConsultation.PENDING);
        consultation.setDoctor(doctor);
        consultation.setPatient(patient);
        consultation.setDate_consultation(consultationDTO.date_consultation());
        consultation.setLocation_consultation(consultationDTO.location_consultation());
        consultation.setReason_consultation(consultationDTO.reason_consultation());
        return consultation;
    }
}
