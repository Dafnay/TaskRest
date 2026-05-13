package com.albavg.rest.dto;

// DTO de entrada para registro, edición de perfil y gestión de usuarios por el admin.
// Centraliza los campos editables del usuario evitando que el cliente envíe
// campos internos como id, role o authHeader.

public record NewUserCommand(String username, String email, String password, String fullname) {
}
