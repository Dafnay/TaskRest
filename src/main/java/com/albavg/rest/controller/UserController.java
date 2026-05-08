package com.albavg.rest.controller;

import com.albavg.rest.dto.NewUserCommand;
import com.albavg.rest.dto.NewUserResponse;
import com.albavg.rest.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints de autenticación y registro")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Permite registrar un nuevo usuario en el sistema"
    )
    @ApiResponse(description = "Usuario creado correctamente",
            responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = NewUserResponse.class),
                    examples = {
                            @ExampleObject("""
                                    {
                                        "id": 1,
                                        "username": "pepe",
                                        "email": "pepe@example.com"
                                    }
                                """)
                    }
            )
    )
    @PostMapping("/auth/register")
    public ResponseEntity<NewUserResponse> createUser(@RequestBody NewUserCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(NewUserResponse.of(userService.register(cmd)));
    }

}