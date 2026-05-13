package com.albavg.rest.dto;

// DTO de salida para usuarios.
// Garantiza que la contraseña nunca se incluya en las respuestas de la API,
// independientemente de cómo evolucione la entidad User.

import com.albavg.rest.model.User;

public record NewUserResponse(Long id, String username, String email, String fullname, String role) {

    public static NewUserResponse of(User user) {
        return new NewUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullname(),
                user.getRole().name()
        );
    }

}
