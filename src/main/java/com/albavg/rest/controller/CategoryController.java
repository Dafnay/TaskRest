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
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Categorías", description = "Endpoints de gestión de categorías")
public class CategoryController {

    private final CategoryService categoryService;

    // ── Usuario autenticado ──────────────────────────────────────────────────

    @Operation(summary = "Listar categorías disponibles", description = "Devuelve todas las categorías. Accesible por cualquier usuario autenticado.")
    @ApiResponse(responseCode = "200", description = "Listado de categorías",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Category.class)),
                    examples = @ExampleObject("""
                            [{"id": 1, "title": "Trabajo"}, {"id": 2, "title": "Personal"}]
                            """)))
    @GetMapping("/categories")
    public List<Category> getAll() {
        return categoryService.findAll();
    }

    // ── Admin ────────────────────────────────────────────────────────────────

    @Operation(summary = "Listar categorías (admin)", description = "Devuelve todas las categorías. Solo ADMIN.")
    @ApiResponse(responseCode = "200", description = "Listado de categorías",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Category.class)),
                    examples = @ExampleObject("""
                            [{"id": 1, "title": "Trabajo"}, {"id": 2, "title": "Personal"}]
                            """)))
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/categories")
    public List<Category> getAllAdmin() {
        return categoryService.findAll();
    }

    @Operation(summary = "Crear categoría (admin)", description = "Crea una nueva categoría. Solo ADMIN.")
    @ApiResponse(responseCode = "201", description = "Categoría creada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Category.class),
                    examples = @ExampleObject("""
                            {"id": 1, "title": "Trabajo"}
                            """)))
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/categories")
    public ResponseEntity<Category> createAdmin(@RequestBody String title) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(title));
    }

    @Operation(summary = "Editar categoría (admin)", description = "Actualiza el título de una categoría. Solo ADMIN.")
    @ApiResponse(responseCode = "200", description = "Categoría actualizada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Category.class),
                    examples = @ExampleObject("""
                            {"id": 1, "title": "Trabajo actualizado"}
                            """)))
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/categories/{id}")
    public Category editAdmin(@PathVariable Long id, @RequestBody String title) {
        return categoryService.edit(id, title);
    }

    @Operation(summary = "Eliminar categoría (admin)", description = "Elimina una categoría. Solo ADMIN.")
    @ApiResponse(responseCode = "204", description = "Categoría eliminada",
            content = @Content(schema = @Schema(implementation = Void.class)))
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ── Gestor ───────────────────────────────────────────────────────────────

    @Operation(summary = "Listar categorías (gestor)", description = "Devuelve todas las categorías. Accesible por GESTOR o ADMIN.")
    @ApiResponse(responseCode = "200", description = "Listado de categorías",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Category.class)),
                    examples = @ExampleObject("""
                            [{"id": 1, "title": "Trabajo"}, {"id": 2, "title": "Personal"}]
                            """)))
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @GetMapping("/manager/categories")
    public List<Category> getAllManager() {
        return categoryService.findAll();
    }

    @Operation(summary = "Crear categoría (gestor)", description = "Crea una nueva categoría. Accesible por GESTOR o ADMIN.")
    @ApiResponse(responseCode = "201", description = "Categoría creada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Category.class),
                    examples = @ExampleObject("""
                            {"id": 1, "title": "Trabajo"}
                            """)))
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @PostMapping("/manager/categories")
    public ResponseEntity<Category> createManager(@RequestBody String title) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(title));
    }

    @Operation(summary = "Editar categoría (gestor)", description = "Actualiza el título de una categoría. Accesible por GESTOR o ADMIN.")
    @ApiResponse(responseCode = "200", description = "Categoría actualizada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Category.class),
                    examples = @ExampleObject("""
                            {"id": 1, "title": "Trabajo actualizado"}
                            """)))
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @PutMapping("/manager/categories/{id}")
    public Category editManager(@PathVariable Long id, @RequestBody String title) {
        return categoryService.edit(id, title);
    }

    @Operation(summary = "Eliminar categoría (gestor)", description = "Elimina una categoría. Accesible por GESTOR o ADMIN.")
    @ApiResponse(responseCode = "204", description = "Categoría eliminada",
            content = @Content(schema = @Schema(implementation = Void.class)))
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @DeleteMapping("/manager/categories/{id}")
    public ResponseEntity<?> deleteManager(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
