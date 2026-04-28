package com.albavg.rest.service;

import com.albavg.rest.model.Category;
import com.albavg.rest.repos.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    public Category save(String title) {
        return categoryRepository.save(
                Category.builder().title(title).build()
        );
    }

    public Category edit(Long id, String title) {
        return categoryRepository.findById(id)
                .map(c -> {
                    c.setTitle(title);
                    return categoryRepository.save(c);
                })
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
