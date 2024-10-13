package com.gerenciamento_medico.medico_api.service;

import com.gerenciamento_medico.medico_api.model.Role;
import com.gerenciamento_medico.medico_api.model.Usuario;
import com.gerenciamento_medico.medico_api.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario cadastrarUsuario(Usuario usuario) {
        usuario.setId(null);
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

            // Verifica se o usuário tem a role de MÉDICO
            if (usuario.getRole() == Role.MEDICO) {
                return Optional.of(usuario);  // Retorna o usuário se for médico
            } else {
                return Optional.empty();  // Retorna vazio se não for médico
            }
        }

        return Optional.empty();  // Retorna vazio se o usuário não for encontrado
    }
}
