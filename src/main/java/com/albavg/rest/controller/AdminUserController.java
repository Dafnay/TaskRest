package com.albavg.rest.controller;

import com.albavg.rest.dto.NewUserCommand;
import com.albavg.rest.dto.NewUserResponse;
import com.albavg.rest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Admin - Usuarios", description = "Gestión de usuarios por el administrador")
public class AdminUserController {

    private final UserService userService;

    @Operation(summary = "Listar todos los usuarios")
    @GetMapping
    public List<NewUserResponse> getAll() {
        return userService.findAll().stream().map(NewUserResponse::of).toList();
    }

    @Operation(summary = "Obtener un usuario por id")
    @GetMapping("/{id}")
    public NewUserResponse getById(@PathVariable Long id) {
        return NewUserResponse.of(userService.findById(id));
    }

    @Operation(summary = "Editar un usuario")
    @PutMapping("/{id}")
    public NewUserResponse edit(@PathVariable Long id, @RequestBody NewUserCommand cmd) {
        return NewUserResponse.of(userService.editUser(id, cmd));
    }

    @Operation(summary = "Eliminar un usuario")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Promocionar usuario a GESTOR")
    @PatchMapping("/{id}/promote")
    public NewUserResponse promote(@PathVariable Long id) {
        return NewUserResponse.of(userService.promote(id));
    }

    @Operation(summary = "Degradar GESTOR a USER")
    @PatchMapping("/{id}/demote")
    public NewUserResponse demote(@PathVariable Long id) {
        return NewUserResponse.of(userService.demote(id));
    }
}
