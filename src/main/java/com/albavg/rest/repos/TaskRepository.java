package com.albavg.rest.repos;

import com.albavg.rest.model.Task;
import com.albavg.rest.model.TaskStatus;
import com.albavg.rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAuthor(User author);
    List<Task> findByAuthorAndTitleContainingIgnoreCase(User author, String title);
    List<Task> findByAuthorAndStatus(User author, TaskStatus status);
    List<Task> findByAuthorAndTagsName(User author, String tagName);
}
