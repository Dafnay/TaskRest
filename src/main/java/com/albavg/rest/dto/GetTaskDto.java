package com.albavg.rest.dto;


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