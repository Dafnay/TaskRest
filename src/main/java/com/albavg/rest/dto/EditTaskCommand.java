package com.albavg.rest.dto;

// DTO de entrada para crear y editar tareas.
// Desacopla la API de la entidad JPA: permite recibir categoryId y tagIds como referencias
// sin exponer la estructura interna de Task ni forzar al cliente a enviar objetos completos.

import com.albavg.rest.model.TaskPriority;
import com.albavg.rest.model.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record EditTaskCommand(
        String title,
        String description,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime deadline,
        TaskStatus status,
        TaskPriority priority,
        String notes,
        Long categoryId,
        List<Long> tagIds
) {
}
