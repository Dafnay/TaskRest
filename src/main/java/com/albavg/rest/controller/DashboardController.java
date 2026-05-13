package com.albavg.rest.controller;

import com.albavg.rest.dto.DashboardDto;
import com.albavg.rest.model.User;
import com.albavg.rest.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Dashboard", description = "Estadísticas de tareas del usuario")
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(
            summary = "Estadísticas del usuario",
            description = "Devuelve un resumen con total de tareas, distribución por estado, prioridad, categoría y tag, tareas vencidas y creadas hoy"
    )
    @GetMapping
    public DashboardDto getDashboard(@AuthenticationPrincipal User author) {
        return dashboardService.getDashboard(author);
    }
}
