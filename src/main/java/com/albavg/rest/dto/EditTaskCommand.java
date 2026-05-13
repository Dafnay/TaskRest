package com.albavg.rest.dto;

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
