package com.albavg.rest.error;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(Long id){
        super("No hay tarea con ese Id: %d".formatted(id));
    }

    public TaskNotFoundException(){
        super("No hay tareas con esos requisitos de búsqueda");
    }

    @Component
    public static class CustomAccessDeniedHandler implements AccessDeniedHandler {

        @Autowired
        @Qualifier("handlerExceptionResolver")
        private HandlerExceptionResolver resolver;

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
            resolver.resolveException(request, response, null, accessDeniedException);
        }
    }
}
