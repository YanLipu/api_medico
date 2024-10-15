package com.gerenciamento_medico.medico_api.controller;

import com.gerenciamento_medico.medico_api.DTO.request.AuthenticationDTO;
import com.gerenciamento_medico.medico_api.DTO.request.LoginDTO;
import com.gerenciamento_medico.medico_api.model.User;
import com.gerenciamento_medico.medico_api.security.JwtToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authorization and Authentication management APIs")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtToken jwtToken;

    @Operation(summary = "Authenticate a user", description = "Authenticates a user and returns a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authentication",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid email or password",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            var emailPassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth =  this.authenticationManager.authenticate(emailPassword);

            var token = jwtToken.generateToken((User) auth.getPrincipal());

            String name = ((User) auth.getPrincipal()).getName();
            String email = ((User) auth.getPrincipal()).getEmail();
            String role = ((User) auth.getPrincipal()).getRole().getRole();

            return ResponseEntity.ok(new LoginDTO(token, name, email, role));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid email or password");
        }

    }
}
