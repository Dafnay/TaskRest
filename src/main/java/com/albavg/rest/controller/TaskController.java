package com.albavg.rest.controller;

import com.albavg.rest.dto.EditTaskCommand;
import com.albavg.rest.dto.GetTaskDto;
import com.albavg.rest.service.TaskService;
import com.albavg.rest.model.Task;
import com.albavg.rest.model.TaskPriority;
import com.albavg.rest.model.TaskStatus;
import com.albavg.rest.model.User;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Tareas", description = "Endpoints de gestion de tareas")
public class TaskController {

        private final TaskService taskService;


        @Operation(
                summary = "Obtener todas las tareas del usuario",
                description = "Permite obtener todas las tareas de un usuario"
        )
        @ApiResponse(description = "Listado de tareas del usuario",
                responseCode = "200",
                content = @Content(
                        mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = GetTaskDto.class)),
                        examples = {
                                @ExampleObject("""
                                    [
                                        {
                                             "id": 1,
                                             "title": "Comprar alimentos",
                                             "description": "Hacer una lista de compras para el supermercado.",
                                             "createdAt": "2026-02-18T16:12:11.295172",
                                             "deadline": "2026-02-25T16:12:11.295172",
                                             "author": {"id": 1, "username": "pepe", "email": "pepe@example.com"},
                                             "category": {"id": 1, "title": "Trabajo"},
                                             "tags": [{"id": 1, "name": "urgente"}]
                                         },
                                         {
                                             "id": 51,
                                             "title": "Pagar facturas",
                                             "description": "Pagar la factura de electricidad antes de la fecha límite.",
                                             "createdAt": "2026-02-18T16:12:11.296628",
                                             "deadline": "2026-02-22T16:12:11.296628",
                                             "author": {"id": 1, "username": "pepe", "email": "pepe@example.com"},
                                             "category": null,
                                             "tags": []
                                         }
                                    ]
                                """)
                        }
                )
        )
        @GetMapping
        public List<GetTaskDto> getAll(@AuthenticationPrincipal User author) {
            //return taskService.findAll()
            return taskService.findByAuthor(author)
                    .stream()
                    .map(GetTaskDto::of)
                    .toList();
        }

        @Operation(summary = "Buscar tareas por tag", description = "Devuelve las tareas del usuario que tienen el tag indicado")
        @GetMapping("by-tag")
        public List<GetTaskDto> getByTag(
                @Parameter(description = "Nombre del tag") @RequestParam String tag,
                @AuthenticationPrincipal User author) {
            return taskService.searchByTag(author, tag)
                    .stream().map(GetTaskDto::of).toList();
        }

        @Operation(summary = "Asignar tags a una tarea", description = "Añade los tags indicados a la tarea sin eliminar los existentes")
        @PreAuthorize("@ownerCheck.check(#id, authentication.principal.getId())")
        @PostMapping("/{id}/tags")
        public GetTaskDto addTags(
                @Parameter(description = "ID de la tarea", required = true) @PathVariable Long id,
                @RequestBody List<Long> tagIds) {
            return GetTaskDto.of(taskService.addTags(id, tagIds));
        }

        @Operation(summary = "Eliminar un tag de una tarea", description = "Desasocia el tag indicado de la tarea")
        @PreAuthorize("@ownerCheck.check(#id, authentication.principal.getId())")
        @DeleteMapping("/{id}/tags/{tagId}")
        public GetTaskDto removeTag(
                @Parameter(description = "ID de la tarea", required = true) @PathVariable Long id,
                @Parameter(description = "ID del tag", required = true) @PathVariable Long tagId) {
            return GetTaskDto.of(taskService.removeTag(id, tagId));
        }

        @Operation(summary = "Buscar tareas", description = "Filtra por título (?title=), estado (?status=PENDING, IN_PROGRESS, COMPLETED) y/o prioridad (?priority=LOW, MEDIUM, HIGH). Al menos un parámetro es obligatorio.")
        @GetMapping("search")
        public ResponseEntity<List<GetTaskDto>> search(
                @Parameter(description = "Texto a buscar en el título") @RequestParam(required = false) String title,
                @Parameter(description = "Estado de la tarea") @RequestParam(required = false) TaskStatus status,
                @Parameter(description = "Prioridad de la tarea") @RequestParam(required = false) TaskPriority priority,
                @AuthenticationPrincipal User author) {
            if (title == null && status == null && priority == null)
                return ResponseEntity.badRequest().build();
            List<Task> result;
            if (title != null)
                result = taskService.searchByTitle(author, title);
            else if (status != null)
                result = taskService.searchByStatus(author, status);
            else
                result = taskService.searchByPriority(author, priority);
            return ResponseEntity.ok(result.stream().map(GetTaskDto::of).toList());
        }

        @Operation(
                summary = "Obtener una tarea concreta",
                description = "Permite obtener la una tarea concreta si se le proporciona un id"
        )
        @ApiResponse(description = "Información detallada de una tarea",
                responseCode = "200",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = GetTaskDto.class),
                        examples = {
                                @ExampleObject("""
                                    {
                                             "id": 1,
                                             "title": "Comprar alimentos",
                                             "description": "Hacer una lista de compras para el supermercado.",
                                             "createdAt": "2026-02-18T16:12:11.295172",
                                             "deadline": "2026-02-25T16:12:11.295172",
                                             "author": {"id": 1, "username": "pepe", "email": "pepe@example.com"},
                                             "category": {"id": 1, "title": "Trabajo"},
                                             "tags": [{"id": 1, "name": "urgente"}]
                                         }
                                """)
                        }
                )
        )
        @PostAuthorize("returnObject.author.username == authentication.principal.username")
        @GetMapping("/{id}")
        public GetTaskDto getById(@Parameter(description = "ID de la tarea", required = true) @PathVariable Long id) {
            return GetTaskDto.of(taskService.findById(id));

        }

        @Operation(
                summary = "Crear una tarea",
                description = "Permite crear una tarea asociada al usuario autenticado"
        )
        @ApiResponse(description = "Tarea recién creada",
                responseCode = "201",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = GetTaskDto.class),
                        examples = {
                                @ExampleObject("""
                                    {
                                             "id": 1,
                                             "title": "Comprar alimentos",
                                             "description": "Hacer una lista de compras para el supermercado.",
                                             "createdAt": "2026-02-18T16:12:11.295172",
                                             "deadline": "2026-02-25T16:12:11.295172",
                                             "author": {"id": 1, "username": "pepe", "email": "pepe@example.com"},
                                             "category": {"id": 1, "title": "Trabajo"},
                                             "tags": [{"id": 1, "name": "urgente"}]
                                         }
                                """)
                        }
                )
        )
        @PostMapping
        public ResponseEntity<GetTaskDto> create(
                @io.swagger.v3.oas.annotations.parameters.RequestBody(
                        description = "Tarea a crear", required = true,
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = EditTaskCommand.class),
                                examples = @ExampleObject("""
                                    {
                                         "title": "Aprender Spring Boot",
                                         "description": "Hacer todos los cursos de Spring Boot en Openwebinars.net",
                                         "deadline": "2026-12-31T23:59:59",
                                         "categoryId": 1,
                                         "tagIds": [1, 2]
                                     }
                                """)
                        )
                )
                @RequestBody EditTaskCommand cmd,
                @AuthenticationPrincipal User author) {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    GetTaskDto.of(taskService.save(cmd, author))
            );
        }


        @Operation(
                summary = "Editar una tarea",
                description = "Permite editar una tarea asociada al usuario autenticado si se proporciona su ID"
        )
        @ApiResponse(description = "Tarea editada",
                responseCode = "200",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = GetTaskDto.class),
                        examples = {
                                @ExampleObject("""
                                    {
                                             "id": 1,
                                             "title": "Comprar alimentos",
                                             "description": "Hacer una lista de compras para el supermercado.",
                                             "createdAt": "2026-02-18T16:12:11.295172",
                                             "deadline": "2026-02-25T16:12:11.295172",
                                             "author": {"id": 1, "username": "pepe", "email": "pepe@example.com"},
                                             "category": {"id": 1, "title": "Trabajo"},
                                             "tags": [{"id": 1, "name": "urgente"}]
                                         }
                                """)
                        }
                )
        )
        @PreAuthorize("""
            @ownerCheck.check(#id, authentication.principal.getId())
            """)
        @PutMapping("/{id}")
        public GetTaskDto edit(
                @io.swagger.v3.oas.annotations.parameters.RequestBody(
                        description = "Datos a editar en la tarea", required = true,
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = EditTaskCommand.class),
                                examples = @ExampleObject("""
                                    {
                                         "title": "Aprender Spring Boot",
                                         "description": "Hacer todos los cursos de Spring Boot en Openwebinars.net",
                                         "deadline": "2026-12-31T23:59:59",
                                         "categoryId": 1,
                                         "tagIds": [1, 2]
                                     }
                                """)
                        )
                )
                @RequestBody EditTaskCommand cmd,
                @Parameter(description = "ID de la tarea", required = true) @PathVariable Long id) {
            return GetTaskDto.of(taskService.edit(cmd, id));
        }

        @Operation(
                summary = "Eliminar una tarea",
                description = "Permite eliminar una tarea asociada al usuario autenticado si se proporciona su ID"
        )
        @ApiResponse(description = "Respuesta correcta de tarea eliminada",
                responseCode = "204",
                content = @Content(schema = @Schema(implementation = Void.class)))
        @PreAuthorize("""
            @ownerCheck.check(#id, authentication.principal.getId())
            """)
        @DeleteMapping("/{id}")
        public ResponseEntity<?> delete(@Parameter(description = "ID de la tarea", required = true) @PathVariable Long id) {
            taskService.delete(id);
            return ResponseEntity.noContent().build();
        }



    }