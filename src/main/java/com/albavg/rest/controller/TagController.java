package com.albavg.rest.controller;

import com.albavg.rest.model.Tag;
import com.albavg.rest.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Tag> getAll() {
        return tagService.findAll();
    }

    @Operation(summary = "Obtener una etiqueta por id", description = "Devuelve una etiqueta según su id")
    @ApiResponse(responseCode = "200", description = "Etiqueta encontrada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Tag.class),
                    examples = @ExampleObject("""
                            {"id": 1, "name": "urgente"}
                            """)))
    @GetMapping("/{id}")
    public Tag getById(@PathVariable Long id) {
        return tagService.findById(id);
    }


    @Operation(summary = "Crear una etiqueta", description = "Crea una nueva etiqueta con el nombre indicado")
    @ApiResponse(responseCode = "201", description = "Etiqueta creada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Tag.class),
                    examples = @ExampleObject("""
                            {"id": 1, "name": "urgente"}
                            """)))
    @PostMapping
    public ResponseEntity<Tag> create(@RequestBody String name) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tagService.save(name));
    }

    @Operation(summary = "Editar una etiqueta", description = "Actualiza el nombre de una etiqueta existente")
    @ApiResponse(responseCode = "200", description = "Etiqueta actualizada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Tag.class),
                    examples = @ExampleObject("""
                            {"id": 1, "name": "urgente actualizado"}
                            """)))
    @PutMapping("/{id}")
    public Tag edit(@PathVariable Long id, @RequestBody String name) {
        return tagService.edit(id, name);
    }

    @Operation(summary = "Eliminar una etiqueta", description = "Elimina una etiqueta según su id")
    @ApiResponse(responseCode = "204", description = "Etiqueta eliminada",
            content = @Content(schema = @Schema(implementation = Void.class)))
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
