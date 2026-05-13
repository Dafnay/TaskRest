package com.albavg.rest.dto;

// DTO de salida para el dashboard.
// Agrega estadísticas calculadas en el servicio sin exponer la lista completa de tareas,
// reduciendo el payload y ocultando detalles internos de las entidades.

import java.util.Map;

public record DashboardDto(
        long totalTasks,
        Map<String, Long> byStatus,
        Map<String, Long> byPriority,
        long overdue,
        long createdToday
) {}
