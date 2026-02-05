package com.albavg.rest.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private String title;

    @Lob
    private String description;

    private LocalDateTime deadline;
}
