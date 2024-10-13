package com.gerenciamento_medico.medico_api.service;

import com.gerenciamento_medico.medico_api.model.Role;
import com.gerenciamento_medico.medico_api.model.User;
import com.gerenciamento_medico.medico_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        user.setId(null);
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        Optional<User> usuarioOptional = userRepository.findById(user.getId());
        if (usuarioOptional.isPresent()) {
            User currentUser = usuarioOptional.get();
            currentUser.setNome(user.getNome());
            currentUser.setEmail(user.getEmail());
            currentUser.setSenha(user.getSenha());
            return userRepository.save(currentUser);
        }
        return null;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findDoctorById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getRole() == Role.DOCTOR) {
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }
}
