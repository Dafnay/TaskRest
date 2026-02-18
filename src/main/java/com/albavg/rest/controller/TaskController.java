package com.albavg.rest.controller;

import com.albavg.rest.dto.EditTaskCommand;
import com.albavg.rest.dto.GetTaskDto;
import com.albavg.rest.model.Task;
import com.albavg.rest.service.TaskService;
import com.albavg.rest.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task/")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @GetMapping
    public List<GetTaskDto> getAll(
            @AuthenticationPrincipal User author
    ) {
        return //taskService.findAll()
                taskService.findByAuthor(author)
                .stream()
                .map(GetTaskDto::of)
                .toList();
    }


    @PostAuthorize("""
            returnObject.author.username == authentication.principal.username
            """)
    @GetMapping("/{id}")
    public GetTaskDto getById(@PathVariable Long id){

        return GetTaskDto.of(taskService.findById(id));
    }


    @PostMapping
    public ResponseEntity<GetTaskDto> create(
            @RequestBody EditTaskCommand cmd,
            @AuthenticationPrincipal User author
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GetTaskDto.of(taskService.save(cmd, author)));
    }


    @PreAuthorize("""
            @ownerCheck.check(#id, authentication.principal.getId())
            """)
    @PutMapping("/{id}")
    public GetTaskDto edit( @RequestBody EditTaskCommand cmd,
                      @PathVariable Long id){
        return GetTaskDto.of(taskService.edit(cmd, id));
    }


    @PreAuthorize("""
            @ownerCheck.check(#id, authentication.principal.getId())
            """)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
