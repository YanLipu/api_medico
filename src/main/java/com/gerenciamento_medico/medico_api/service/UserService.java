package com.gerenciamento_medico.medico_api.service;

import com.gerenciamento_medico.medico_api.DTO.request.RegisterUserDTO;
import com.gerenciamento_medico.medico_api.DTO.response.RegisterUserResponseDTO;
import com.gerenciamento_medico.medico_api.model.Role;
import com.gerenciamento_medico.medico_api.model.User;
import com.gerenciamento_medico.medico_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public RegisterUserResponseDTO registerUser(RegisterUserDTO user) {
        if (this.userRepository.findByEmail(user.email()) != null) {
            throw new IllegalArgumentException("Email address already in use");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(user.password());

        User newUser = new User();
        newUser.setEmail(user.email());
        newUser.setPassword(encryptedPassword);
        newUser.setRole(Role.valueOf(user.role()));
        newUser.setName(user.name());

        this.userRepository.save(newUser);

        return new RegisterUserResponseDTO(
                newUser.getName(),
                newUser.getEmail(),
                newUser.getRole()
        );
    }

    public User updateUser(User user) {
        Optional<User> usuarioOptional = userRepository.findById(user.getId());
        if (usuarioOptional.isPresent()) {
            User currentUser = usuarioOptional.get();
            currentUser.setName(user.getName());
            currentUser.setEmail(user.getEmail());
            currentUser.setPassword(user.getPassword());
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
