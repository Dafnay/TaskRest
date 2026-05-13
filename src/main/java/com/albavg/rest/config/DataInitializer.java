package com.albavg.rest.config;

import com.albavg.rest.model.*;
import com.albavg.rest.repos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() > 0) return;

        User admin = userRepository.save(User.builder()
                .username("admin")
                .email("admin@example.com")
                .fullname("Administrador")
                .password(passwordEncoder.encode("admin"))
                .role(UserRole.ADMIN)
                .build());

        User user1 = userRepository.save(User.builder()
                .username("user1")
                .email("user1@example.com")
                .fullname("Usuario Uno")
                .password(passwordEncoder.encode("12345"))
                .role(UserRole.USER)
                .build());

        userRepository.save(User.builder()
                .username("user2")
                .email("user2@example.com")
                .fullname("Usuario Dos")
                .password(passwordEncoder.encode("12345"))
                .role(UserRole.USER)
                .build());

        Category categoria = categoryRepository.save(Category.builder()
                .title("Categoría 1")
                .build());

        tagRepository.save(Tag.builder()
                .name("pendiente")
                .build());

        taskRepository.save(Task.builder()
                .title("Mi primera tarea")
                .description("Tarea de ejemplo creada al iniciar la aplicación")
                .deadline(LocalDateTime.now().plusDays(7))
                .status(TaskStatus.IN_PROGRESS)
                .priority(TaskPriority.HIGH)
                .notes("Revisar antes de entregar")
                .author(user1)
                .category(categoria)
                .build());
    }
}
