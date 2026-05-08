package com.albavg.rest.dto;


import com.albavg.rest.model.Category;
import com.albavg.rest.model.Tag;
import com.albavg.rest.model.Task;
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
        boolean completed,
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
                t.isCompleted(),
                t.getCategory(),
                t.getTags()
        );
    }

}