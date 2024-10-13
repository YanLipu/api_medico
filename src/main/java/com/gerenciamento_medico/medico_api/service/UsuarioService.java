package com.gerenciamento_medico.medico_api.service;

import com.gerenciamento_medico.medico_api.model.Role;
import com.gerenciamento_medico.medico_api.model.Usuario;
import com.gerenciamento_medico.medico_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

//    @Autowired
//    private PasswordEncoder criptografiaSenha;

    public Usuario cadastrarUsuario(Usuario usuario) {
        usuario.setId(null);
//        usuario.setSenha(criptografiaSenha.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuario.getId());
        if (usuarioOptional.isPresent()) {
            Usuario usuarioAtual = usuarioOptional.get();
            usuarioAtual.setNome(usuario.getNome());
            usuarioAtual.setEmail(usuario.getEmail());
            usuarioAtual.setSenha(usuario.getSenha());
            return usuarioRepository.save(usuarioAtual);
        }
        return null;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarMedicoPorId(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            if (usuario.getRole() == Role.MEDICO) {
                return Optional.of(usuario);
            } else {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }
}
