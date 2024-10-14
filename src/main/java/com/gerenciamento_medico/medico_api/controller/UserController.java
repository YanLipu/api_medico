package com.gerenciamento_medico.medico_api.controller;

import com.gerenciamento_medico.medico_api.DTO.request.RegisterUserDTO;
import com.gerenciamento_medico.medico_api.DTO.response.RegisterUserResponseDTO;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<RegisterUserResponseDTO> registerUser(@RequestBody @Valid RegisterUserDTO user) {
        RegisterUserResponseDTO registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping
    public List<User> listUsers() {
        return userService.listUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
