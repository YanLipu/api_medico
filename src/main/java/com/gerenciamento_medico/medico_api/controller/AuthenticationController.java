package com.gerenciamento_medico.medico_api.controller;

import com.gerenciamento_medico.medico_api.DTO.request.AuthenticationDTO;
import com.gerenciamento_medico.medico_api.DTO.request.LoginDTO;
import com.gerenciamento_medico.medico_api.model.User;
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
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtToken jwtToken;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            var emailPassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth =  this.authenticationManager.authenticate(emailPassword);

            var token = jwtToken.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new LoginDTO(token));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid email or password");
        }

    }
}
