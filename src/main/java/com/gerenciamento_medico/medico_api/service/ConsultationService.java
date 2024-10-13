package com.gerenciamento_medico.medico_api.service;

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

    public Consultation requestConsultation(Consultation consultation) {
        Optional<User> medico = this.userService.findDoctorById(consultation.getMedico().getId());
        if (medico.isEmpty()) {
           throw new IllegalArgumentException("Médico não encontrado.");
        }
        if (medico.get().getRole() != Role.DOCTOR) {
           throw new IllegalArgumentException("O usuário " + medico.get().getId() + "não é médico");
        }

        Optional<User> paciente = this.userService.findUserById(consultation.getPaciente().getId());
        if (paciente.isEmpty()) {
            throw new IllegalArgumentException("Paciente inválido");
        }
        if (paciente.get().getRole() != Role.PATIENT ) {
            throw new IllegalArgumentException("O usuário " + paciente.get().getId() + "não é paciente");
        }

        Consultation consultationRequested = new Consultation();
        consultationRequested.setStatus(StatusConsultation.PENDING);
        consultationRequested.setMedico(medico.get());
        consultationRequested.setPaciente(paciente.get());
        consultationRequested.setDataConsulta(consultation.getDataConsulta());
        return consultationRepository.save(consultationRequested);
    }

    public List<Consultation> listarConsultas() {
        return consultationRepository.findAll();
    }

    public Consultation aprovarConsulta(Long id) {
        Optional<Consultation> consultaOptional = consultationRepository.findById(id);
        if (consultaOptional.isPresent()) {
            Consultation consultation = consultaOptional.get();
            consultation.setStatus(StatusConsultation.APPROVED);
            return consultationRepository.save(consultation);
        }
        return null;
    }

    public Consultation reprovarConsulta(Long id) {
        Optional<Consultation> consultaOptional = consultationRepository.findById(id);
        if (consultaOptional.isPresent()) {
            Consultation consultation = consultaOptional.get();
            consultation.setStatus(StatusConsultation.REJECTED);
            return consultationRepository.save(consultation);
        }
        return null;
    }

    public Consultation finalizarConsulta(Long id, String parecerMedico) {
        Optional<Consultation> consultaOptional = consultationRepository.findById(id);
        if (consultaOptional.isPresent()) {
            Consultation consultation = consultaOptional.get();
            consultation.setStatus(StatusConsultation.COMPLETED);
            consultation.setParecerMedico(parecerMedico);
            return consultationRepository.save(consultation);
        }
        return null;
    }
}
