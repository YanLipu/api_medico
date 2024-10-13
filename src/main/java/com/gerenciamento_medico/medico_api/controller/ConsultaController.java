package com.gerenciamento_medico.medico_api.controller;

import com.gerenciamento_medico.medico_api.DTO.ConsultaDTO;
import com.gerenciamento_medico.medico_api.model.Consulta;
import com.gerenciamento_medico.medico_api.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    public ResponseEntity<Consulta> solicitarConsulta(@RequestBody Consulta consulta) {
        Consulta novaConsulta = consultaService.solicitarConsulta(consulta);
        return ResponseEntity.ok(novaConsulta);
    }

    @GetMapping
    public List<Consulta> listarConsultas() {
        return consultaService.listarConsultas();
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<Consulta> aprovarConsulta(@PathVariable Long id) {
        Consulta consultaAprovada = consultaService.aprovarConsulta(id);
        return consultaAprovada != null ? ResponseEntity.ok(consultaAprovada) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Consulta> finalizarConsulta(@PathVariable Long id, @RequestBody String parecerMedico) {
        Consulta consultaFinalizada = consultaService.finalizarConsulta(id, parecerMedico);
        return consultaFinalizada != null ? ResponseEntity.ok(consultaFinalizada) : ResponseEntity.notFound().build();
    }
}
