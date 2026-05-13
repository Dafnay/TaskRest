package com.albavg.rest.service;

import com.albavg.rest.dto.DashboardDto;
import com.albavg.rest.model.Task;
import com.albavg.rest.model.TaskStatus;
import com.albavg.rest.model.User;
import com.albavg.rest.repos.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TaskRepository taskRepository;

    public DashboardDto getDashboard(User author) {
        List<Task> tasks = taskRepository.findByAuthor(author);
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime now = LocalDateTime.now();

        Map<String, Long> byStatus = tasks.stream()
                .collect(Collectors.groupingBy(t -> t.getStatus().name(), Collectors.counting()));

        Map<String, Long> byPriority = tasks.stream()
                .filter(t -> t.getPriority() != null)
                .collect(Collectors.groupingBy(t -> t.getPriority().name(), Collectors.counting()));

        long overdue = tasks.stream()
                .filter(t -> t.getDeadline() != null
                        && t.getDeadline().isBefore(now)
                        && t.getStatus() != TaskStatus.COMPLETED)
                .count();

        long createdToday = tasks.stream()
                .filter(t -> t.getCreatedAt().isAfter(todayStart))
                .count();

        return new DashboardDto(tasks.size(), byStatus, byPriority, overdue, createdToday);
    }
}
