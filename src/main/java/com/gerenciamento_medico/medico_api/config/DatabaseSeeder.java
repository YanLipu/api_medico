package com.gerenciamento_medico.medico_api.config;

import com.gerenciamento_medico.medico_api.model.User;
import com.gerenciamento_medico.medico_api.model.Role;
import com.gerenciamento_medico.medico_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            seedUsers();
        }
    }

    private void seedUsers() {
        User user1 = new User();
        user1.setEmail("neymar@teste.com");
        user1.setName("Neymar Júnior");
        user1.setPassword("$2a$10$hvzFDRrL.x/ZxOSyTk9p/emC6iuLvnUJigJJkqgIOeL.3fU4JixKq");
        user1.setRole(Role.ADMIN);
        userRepository.save(user1);

        User user2 = new User();
        user2.setEmail("messi@teste.com");
        user2.setName("Lionel Messi");
        user2.setPassword("$2a$10$XoqRSSdVYSTz2R5E/Zxu3uHUY1IWy/tjltgFzlecX0PWzGurw6Qmy");
        user2.setRole(Role.DOCTOR);
        userRepository.save(user2);

        User user3 = new User();
        user3.setEmail("r10@teste.com");
        user3.setName("Ronaldinho Gaúcho");
        user3.setPassword("$2a$10$sSlYB41vnNqOmk5tVKUynuDEdDcWtyJIg.Vg/sqc6c9GCP90uY87q");
        user3.setRole(Role.PATIENT);
        userRepository.save(user3);

        User user4 = new User();
        user4.setEmail("rogerio@teste.com");
        user4.setName("Rogério Ceno");
        user4.setPassword("$2a$10$ixneFXjgSmKrk/kQjRsUI.9vQqBO4b9yI5V0rsgR/./rWaQQ3zLsG");
        user4.setRole(Role.NURSE);
        userRepository.save(user4);

        User user5 = new User();
        user5.setEmail("lf@teste.com");
        user5.setName("Luis Fabiano");
        user5.setPassword("$2a$10$DlGmL3UrN21Q9Rm2mGARReEHS0qeSfxY4p1ZpQtsZcQ6/sPFSVsdu");
        user5.setRole(Role.DOCTOR);
        userRepository.save(user5);
    }
}