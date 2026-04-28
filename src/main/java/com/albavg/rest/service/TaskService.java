package com.albavg.rest.service;

import com.albavg.rest.dto.EditTaskCommand;
import com.albavg.rest.error.TaskNotFoundException;
import com.albavg.rest.model.Category;
import com.albavg.rest.model.Tag;
import com.albavg.rest.model.Task;
import com.albavg.rest.repos.CategoryRepository;
import com.albavg.rest.repos.TagRepository;
import com.albavg.rest.repos.TaskRepository;
import com.albavg.rest.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public List<Task> findAll(){
        List<Task> result = taskRepository.findAll();

        if(result.isEmpty())
            throw new TaskNotFoundException();
        return result;
    }

    public List<Task> findByAuthor(User author){
        List<Task> result = taskRepository.findByAuthor(author);

        if(result.isEmpty())
            throw new TaskNotFoundException();
        return result;
    }


    public Task findById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task save(EditTaskCommand cmd, User author){
        Category category = cmd.categoryId() != null
                ? categoryRepository.findById(cmd.categoryId()).orElse(null)
                : null;

        List<Tag> tags = cmd.tagIds() != null
                ? tagRepository.findAllById(cmd.tagIds())
                : new ArrayList<>();

        return taskRepository.save(
                Task.builder()
                        .title(cmd.title())
                        .description(cmd.description())
                        .deadline(cmd.deadline())
                        .completed(cmd.completed())
                        .author(author)
                        .category(category)
                        .tags(tags)
                        .build()
        );
    }

    public Task edit(EditTaskCommand cmd, Long id){
        Category category = cmd.categoryId() != null
                ? categoryRepository.findById(cmd.categoryId()).orElse(null)
                : null;

        List<Tag> tags = cmd.tagIds() != null
                ? tagRepository.findAllById(cmd.tagIds())
                : new ArrayList<>();

        return taskRepository.findById(id)
                .map(t -> {
                    t.setTitle(cmd.title());
                    t.setDescription(cmd.description());
                    t.setDeadline(cmd.deadline());
                    t.setCompleted(cmd.completed());
                    t.setCategory(category);
                    t.setTags(tags);
                    return taskRepository.save(t);
                })
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public void delete(Long id){
        taskRepository.deleteById(id);

    }
}
