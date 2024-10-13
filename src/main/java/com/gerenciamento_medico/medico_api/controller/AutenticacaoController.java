package com.gerenciamento_medico.medico_api.controller;

import com.gerenciamento_medico.medico_api.DTO.AutenticacaoDTO;
import com.gerenciamento_medico.medico_api.DTO.LoginDTO;
import com.gerenciamento_medico.medico_api.model.Usuario;
import com.gerenciamento_medico.medico_api.security.JwtToken;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtToken jwtToken;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticacaoDTO data) {
        try {
            var emailSenha = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
            var auth =  this.authenticationManager.authenticate(emailSenha);

            var token = jwtToken.generateToken((Usuario) auth.getPrincipal());

            return ResponseEntity.ok(new LoginDTO(token));
        } catch (Exception e) {
            throw new IllegalArgumentException("Usuário ou senha inválidos");
        }

    }
}
