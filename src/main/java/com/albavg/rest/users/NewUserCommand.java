package com.albavg.rest.users;

public record NewUserCommand(String username, String email, String password, String fullname) {
}
