package com.gerenciamento_medico.medico_api.controller;

import com.gerenciamento_medico.medico_api.DTO.RegistroUsuarioDTO;
import com.gerenciamento_medico.medico_api.model.Role;
import com.gerenciamento_medico.medico_api.model.Usuario;
import com.gerenciamento_medico.medico_api.repository.UsuarioRepository;
import com.gerenciamento_medico.medico_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody @Valid RegistroUsuarioDTO usuario) {
        if(this.usuarioRepository.findByEmail(usuario.email()) != null) return ResponseEntity.badRequest().build();

        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.senha());

        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(usuario.email());
        novoUsuario.setSenha(senhaCriptografada);
        novoUsuario.setRole(Role.valueOf(usuario.role()));
        novoUsuario.setNome(usuario.nome());

        this.usuarioRepository.save(novoUsuario);

        return ResponseEntity.ok(novoUsuario);
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.buscarUsuarioPorId(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
