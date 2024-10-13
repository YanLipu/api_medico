package com.gerenciamento_medico.medico_api.service;

import com.gerenciamento_medico.medico_api.model.Consulta;
import com.gerenciamento_medico.medico_api.model.Role;
import com.gerenciamento_medico.medico_api.model.StatusConsulta;
import com.gerenciamento_medico.medico_api.model.Usuario;
import com.gerenciamento_medico.medico_api.repository.ConsultaRepository;
import com.gerenciamento_medico.medico_api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private UsuarioService usuarioService;

    public Consulta solicitarConsulta(Consulta consulta) {
        Optional<Usuario> medico = this.usuarioService.buscarMedicoPorId(consulta.getMedico().getId());
        if (medico.isEmpty()) {
           throw new IllegalArgumentException("Médico não encontrado.");
        }
        if (medico.get().getRole() != Role.MEDICO) {
           throw new IllegalArgumentException("O usuário " + medico.get().getId() + "não é médico");
        }

        Optional<Usuario> paciente = this.usuarioService.buscarUsuarioPorId(consulta.getPaciente().getId());
        if (paciente.isEmpty()) {
            throw new IllegalArgumentException("Paciente inválido");
        }
        if (paciente.get().getRole() != Role.PACIENTE ) {
            throw new IllegalArgumentException("O usuário " + paciente.get().getId() + "não é paciente");
        }

        Consulta consultaSolicitada = new Consulta();
        consultaSolicitada.setStatus(StatusConsulta.PENDENTE);
        consultaSolicitada.setMedico(medico.get());
        consultaSolicitada.setPaciente(paciente.get());
        consultaSolicitada.setDataConsulta(consulta.getDataConsulta());
        return consultaRepository.save(consultaSolicitada);
    }

    public List<Consulta> listarConsultas() {
        return consultaRepository.findAll();
    }

    public Consulta aprovarConsulta(Long id) {
        Optional<Consulta> consultaOptional = consultaRepository.findById(id);
        if (consultaOptional.isPresent()) {
            Consulta consulta = consultaOptional.get();
            consulta.setStatus(StatusConsulta.APROVADA);
            return consultaRepository.save(consulta);
        }
        return null;
    }

    public Consulta reprovarConsulta(Long id) {
        Optional<Consulta> consultaOptional = consultaRepository.findById(id);
        if (consultaOptional.isPresent()) {
            Consulta consulta = consultaOptional.get();
            consulta.setStatus(StatusConsulta.REPROVADA);
            return consultaRepository.save(consulta);
        }
        return null;
    }

    public Consulta finalizarConsulta(Long id, String parecerMedico) {
        Optional<Consulta> consultaOptional = consultaRepository.findById(id);
        if (consultaOptional.isPresent()) {
            Consulta consulta = consultaOptional.get();
            consulta.setStatus(StatusConsulta.FINALIZADA);
            consulta.setParecerMedico(parecerMedico);
            return consultaRepository.save(consulta);
        }
        return null;
    }
}
