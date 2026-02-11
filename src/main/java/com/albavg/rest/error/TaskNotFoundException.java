package com.albavg.rest.error;

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
}
