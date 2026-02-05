package com.albavg.rest.service;

import com.albavg.rest.repos.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;
}
