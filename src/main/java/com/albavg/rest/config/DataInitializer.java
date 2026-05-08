package com.albavg.rest.config;

import com.albavg.rest.model.User;
import com.albavg.rest.model.UserRole;
import com.albavg.rest.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() == 0) {
            userRepository.save(User.builder()
                    .username("admin")
                    .email("admin@example.com")
                    .fullname("Administrador")
                    .password(passwordEncoder.encode("admin"))
                    .role(UserRole.ADMIN)
                    .build());

            userRepository.save(User.builder()
                    .username("gestor")
                    .email("gestor@example.com")
                    .fullname("Gestor Principal")
                    .password(passwordEncoder.encode("gestor"))
                    .role(UserRole.GESTOR)
                    .build());

            userRepository.save(User.builder()
                    .username("pepe")
                    .email("pepe@example.com")
                    .fullname("Pepe García")
                    .password(passwordEncoder.encode("12345"))
                    .role(UserRole.USER)
                    .build());
        }
    }
}
