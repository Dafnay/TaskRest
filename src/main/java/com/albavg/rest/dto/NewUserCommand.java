package com.albavg.rest.dto;

public record NewUserCommand(String username, String email, String password, String fullname) {
}
