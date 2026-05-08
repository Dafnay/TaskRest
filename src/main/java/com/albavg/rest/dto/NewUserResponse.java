package com.albavg.rest.dto;

import com.albavg.rest.model.User;

public record NewUserResponse(Long id, String username, String email, String fullname) {

    public static NewUserResponse of(User user) {
        return new NewUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullname()
        );
    }

}
