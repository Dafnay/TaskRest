package com.albavg.rest.model;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private String title;

    @Lob
    private String description;

    private LocalDateTime deadline;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Lob
    private String notes;

    @ManyToOne
    private User author;

    @ManyToOne
    private Category category;

    @ManyToMany
    @Builder.Default
    private List<Tag> tags = new ArrayList<>();

}
