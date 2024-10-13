package com.gerenciamento_medico.medico_api.controller;

import com.gerenciamento_medico.medico_api.model.Consultation;
import com.gerenciamento_medico.medico_api.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consulta")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @PostMapping
    public ResponseEntity<Consultation> solicitarConsulta(@RequestBody Consultation consultation) {
        Consultation newConsultation = consultationService.requestConsultation(consultation);
        return ResponseEntity.ok(newConsultation);
    }

    @GetMapping
    public List<Consultation> listarConsultas() {
        return consultationService.listarConsultas();
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<Consultation> aprovarConsulta(@PathVariable Long id) {
        Consultation consultationApproved = consultationService.aprovarConsulta(id);
        return consultationApproved != null ? ResponseEntity.ok(consultationApproved) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Consultation> finalizarConsulta(@PathVariable Long id, @RequestBody String parecerMedico) {
        Consultation consultationFinalized = consultationService.finalizarConsulta(id, parecerMedico);
        return consultationFinalized != null ? ResponseEntity.ok(consultationFinalized) : ResponseEntity.notFound().build();
    }
}
