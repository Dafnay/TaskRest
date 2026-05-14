package com.albavg.rest.controller;

import com.albavg.rest.model.Tag;
import com.albavg.rest.model.User;
import com.albavg.rest.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag/")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Etiquetas", description = "Endpoints de gestión de etiquetas")
public class TagController {

    private final TagService tagService;

    @Operation(summary = "Obtener todas las etiquetas", description = "Devuelve el listado completo de etiquetas")
    @ApiResponse(responseCode = "200", description = "Listado de etiquetas",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Tag.class)),
                    examples = @ExampleObject("""
                            [{"id": 1, "name": "urgente"}, {"id": 2, "name": "pendiente"}]
                            """)))
    @GetMapping
    public List<Tag> getAll(@AuthenticationPrincipal User currentUser) {
        return tagService.findAll(currentUser);
    }

    @Operation(summary = "Obtener una etiqueta por id", description = "Devuelve una etiqueta según su id")
    @ApiResponse(responseCode = "200", description = "Etiqueta encontrada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Tag.class),
                    examples = @ExampleObject("""
                            {"id": 1, "name": "urgente"}
                            """)))
    @GetMapping("/{id}")
    public Tag getById(@Parameter(description = "ID de la etiqueta", required = true) @PathVariable Long id,
                       @AuthenticationPrincipal User currentUser) {
        return tagService.findById(id, currentUser);
    }


    @Operation(summary = "Crear una etiqueta", description = "Crea una nueva etiqueta con el nombre indicado")
    @ApiResponse(responseCode = "201", description = "Etiqueta creada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Tag.class),
                    examples = @ExampleObject("""
                            {"id": 1, "name": "urgente"}
                            """)))
    @PostMapping
    public ResponseEntity<Tag> create(@RequestBody String name, @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tagService.save(name, currentUser));
    }

    @Operation(summary = "Editar una etiqueta", description = "Actualiza el nombre de una etiqueta existente")
    @ApiResponse(responseCode = "200", description = "Etiqueta actualizada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Tag.class),
                    examples = @ExampleObject("""
                            {"id": 1, "name": "urgente actualizado"}
                            """)))
    @PutMapping("/{id}")
    public Tag edit(@Parameter(description = "ID de la etiqueta", required = true) @PathVariable Long id,
                    @RequestBody String name,
                    @AuthenticationPrincipal User currentUser) {
        return tagService.edit(id, name, currentUser);
    }

    @Operation(summary = "Eliminar una etiqueta", description = "Elimina una etiqueta según su id")
    @ApiResponse(responseCode = "204", description = "Etiqueta eliminada",
            content = @Content(schema = @Schema(implementation = Void.class)))
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@Parameter(description = "ID de la etiqueta", required = true) @PathVariable Long id,
                                    @AuthenticationPrincipal User currentUser) {
        tagService.delete(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}
