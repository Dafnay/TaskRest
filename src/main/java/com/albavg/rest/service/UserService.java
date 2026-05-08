package com.albavg.rest.service;

import com.albavg.rest.dto.NewUserCommand;
import com.albavg.rest.model.User;
import com.albavg.rest.model.UserRole;
import com.albavg.rest.repos.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(NewUserCommand cmd) {
        User user = User.builder()
                .username(cmd.username())
                .email(cmd.email())
                .password(passwordEncoder.encode(cmd.password()))
                .fullname(cmd.fullname())
                .role(UserRole.USER)
                .build();
        return userRepository.save(user);
    }

    public User getProfile(User user) {
        return user;
    }

    public User editProfile(User user, NewUserCommand cmd) {
        user.setUsername(cmd.username());
        user.setEmail(cmd.email());
        user.setFullname(cmd.fullname());
        if (cmd.password() != null && !cmd.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(cmd.password()));
        }
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
