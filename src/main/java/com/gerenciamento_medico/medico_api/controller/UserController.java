package com.gerenciamento_medico.medico_api.controller;

import com.gerenciamento_medico.medico_api.DTO.RegisterUserDTO;
import com.gerenciamento_medico.medico_api.model.Role;
import com.gerenciamento_medico.medico_api.model.User;
import com.gerenciamento_medico.medico_api.repository.UserRepository;
import com.gerenciamento_medico.medico_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<User> cadastrarUsuario(@RequestBody @Valid RegisterUserDTO usuario) {
        if(this.userRepository.findByEmail(usuario.email()) != null) return ResponseEntity.badRequest().build();

        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.senha());

        User newUser = new User();
        newUser.setEmail(usuario.email());
        newUser.setSenha(senhaCriptografada);
        newUser.setRole(Role.valueOf(usuario.role()));
        newUser.setNome(usuario.nome());

        this.userRepository.save(newUser);

        return ResponseEntity.ok(newUser);
    }

    @GetMapping
    public List<User> listarUsuarios() {
        return userService.listUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> buscarUsuarioPorId(@PathVariable Long id) {
        Optional<User> usuario = userService.findUserById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
