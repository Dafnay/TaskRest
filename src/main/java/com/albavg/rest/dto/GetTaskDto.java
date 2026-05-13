package com.albavg.rest.dto;

// DTO de salida para tareas.
// Evita serializar la entidad JPA directamente, lo que podría exponer relaciones lazy,
// referencias circulares (Task -> User -> ...) o campos sensibles.
// Incluye author como NewUserResponse para no exponer la contraseña del usuario.


import com.albavg.rest.model.*;
import com.albavg.rest.dto.NewUserResponse;

import java.time.LocalDateTime;
import java.util.List;

public record GetTaskDto(
        Long id,
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime deadline,
        NewUserResponse author,
        TaskStatus status,
        TaskPriority priority,
        String notes,
        Category category,
        List<Tag> tags){

    public static GetTaskDto of(Task t) {
        return new GetTaskDto(
                t.getId(),
                t.getTitle(),
                t.getDescription(),
                t.getCreatedAt(),
                t.getDeadline(),
                NewUserResponse.of(t.getAuthor()),
                t.getStatus(),
                t.getPriority(),
                t.getNotes(),
                t.getCategory(),
                t.getTags()
        );
    }

}