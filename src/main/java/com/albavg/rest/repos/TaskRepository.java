package com.albavg.rest.repos;

import com.albavg.rest.model.Task;
import com.albavg.rest.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAuthor(User author);
}
