package com.albavg.rest.controller;

import com.albavg.rest.dto.NewTaskCommand;
import com.albavg.rest.model.Task;
import com.albavg.rest.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task/")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<Task> getAll(){
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public Task getById(@PathVariable Long id){
        return taskService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Task> create(
            @RequestBody NewTaskCommand cmd
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.save(cmd));
    }

}
