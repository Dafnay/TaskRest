package com.albavg.rest.controller;

import com.albavg.rest.model.Category;
import com.albavg.rest.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category/")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Categorías", description = "Endpoints de gestión de categorías")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Obtener todas las categorías", description = "Devuelve el listado completo de categorías")
    @ApiResponse(responseCode = "200", description = "Listado de categorías",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Category.class)),
                    examples = @ExampleObject("""
                            [{"id": 1, "title": "Trabajo"}, {"id": 2, "title": "Personal"}]
                            """)))
    @GetMapping
    public List<Category> getAll() {
        return categoryService.findAll();
    }

    @Operation(summary = "Obtener una categoría por id", description = "Devuelve una categoría según su id")
    @ApiResponse(responseCode = "200", description = "Categoría encontrada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Category.class),
                    examples = @ExampleObject("""
                            {"id": 1, "title": "Trabajo"}
                            """)))
    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @Operation(summary = "Crear una categoría", description = "Crea una nueva categoría con el título indicado")
    @ApiResponse(responseCode = "201", description = "Categoría creada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Category.class),
                    examples = @ExampleObject("""
                            {"id": 1, "title": "Trabajo"}
                            """)))

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @PostMapping
    public ResponseEntity<Category> create(@RequestBody String title) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.save(title));
    }


    @Operation(summary = "Editar una categoría", description = "Actualiza el título de una categoría existente")
    @ApiResponse(responseCode = "200", description = "Categoría actualizada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Category.class),
                    examples = @ExampleObject("""
                            {"id": 1, "title": "Trabajo actualizado"}
                            """)))
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @PutMapping("/{id}")
    public Category edit(@PathVariable Long id, @RequestBody String title) {
        return categoryService.edit(id, title);
    }

    @Operation(summary = "Eliminar una categoría", description = "Elimina una categoría según su id")
    @ApiResponse(responseCode = "204", description = "Categoría eliminada",
            content = @Content(schema = @Schema(implementation = Void.class)))
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
