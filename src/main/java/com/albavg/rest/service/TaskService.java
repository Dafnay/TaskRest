package com.albavg.rest.service;

import com.albavg.rest.dto.NewTaskCommand;
import com.albavg.rest.error.TaskNotFoundException;
import com.albavg.rest.model.Task;
import com.albavg.rest.repos.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> findAll(){
        List<Task> result = taskRepository.findAll();

        if(result.isEmpty())
            throw new TaskNotFoundException();
        return result;
    }

    public Task findById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task save(NewTaskCommand cmd){
        return taskRepository.save(
                Task.builder()
                        .title(cmd.title())
                        .description(cmd.description())
                        .deadline(cmd.deadline())
                        .build()
        );
    }
}
