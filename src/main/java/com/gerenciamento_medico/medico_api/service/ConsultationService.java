package com.gerenciamento_medico.medico_api.service;

import com.gerenciamento_medico.medico_api.DTO.request.ConsultationDTO;
import com.gerenciamento_medico.medico_api.DTO.response.ConsultationResponseDTO;
import com.gerenciamento_medico.medico_api.DTO.response.DoctorResponseDTO;
import com.gerenciamento_medico.medico_api.DTO.response.PatientResponseDTO;
import com.gerenciamento_medico.medico_api.model.Consultation;
import com.gerenciamento_medico.medico_api.model.Role;
import com.gerenciamento_medico.medico_api.model.StatusConsultation;
import com.gerenciamento_medico.medico_api.model.User;
import com.gerenciamento_medico.medico_api.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private UserService userService;

    public ConsultationResponseDTO requestConsultation(ConsultationDTO consultation) {
        Optional<User> medico = this.userService.findDoctorById(consultation.doctor_id());
        if (medico.isEmpty()) {
           throw new IllegalArgumentException("Doctor not found");
        }
        if (medico.get().getRole() != Role.DOCTOR) {
           throw new IllegalArgumentException("The user " + medico.get().getId() + "is not doctor.");
        }

        Optional<User> patient = this.userService.findUserById(consultation.patient_id());
        if (patient.isEmpty()) {
            throw new IllegalArgumentException("Invalid patient");
        }
        if (patient.get().getRole() != Role.PATIENT ) {
            throw new IllegalArgumentException("The user " + patient.get().getId() + "is not patient.");
        }

        Consultation consultationRequested = new Consultation();
        consultationRequested.setStatus(StatusConsultation.PENDING);
        consultationRequested.setDoctor(medico.get());
        consultationRequested.setPatient(patient.get());
        consultationRequested.setDate_consultation(consultation.date_consultation());
        consultationRequested.setLocation_consultation(consultation.location_consultation());
        consultationRequested.setReason_consultation(consultation.reason_consultation());

        Consultation savedConsultation = consultationRepository.save(consultationRequested);

        DoctorResponseDTO savedDoctor = new DoctorResponseDTO(savedConsultation.getDoctor().getId(), savedConsultation.getDoctor().getName());
        PatientResponseDTO savedPatient = new PatientResponseDTO(savedConsultation.getPatient().getId(), savedConsultation.getPatient().getName());

        return new ConsultationResponseDTO(
                savedConsultation.getId(),
                savedConsultation.getDate_consultation(),
                savedDoctor,
                savedPatient
        );
    }

    public List<Consultation> listConsultations() {
        return consultationRepository.findAll();
    }

    public Consultation approveConsultation(Long id) {
        Optional<Consultation> consultaOptional = consultationRepository.findById(id);
        if (consultaOptional.isPresent()) {
            Consultation consultation = consultaOptional.get();
            consultation.setStatus(StatusConsultation.APPROVED);
            return consultationRepository.save(consultation);
        }
        return null;
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

    public Consultation finishConsultation(Long id, String medicalObservation) {
        Optional<Consultation> consultaOptional = consultationRepository.findById(id);
        if (consultaOptional.isPresent()) {
            Consultation consultation = consultaOptional.get();
            consultation.setStatus(StatusConsultation.COMPLETED);
            consultation.setMedical_observation(medicalObservation);
            return consultationRepository.save(consultation);
        }
        return null;
    }
}
