package com.albavg.rest.service;

import com.albavg.rest.model.Tag;
import com.albavg.rest.repos.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Tag findById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag no encontrado"));
    }

    public Tag save(String name) {
        return tagRepository.save(
                Tag.builder().name(name).build()
        );
    }

    public Tag edit(Long id, String name) {
        return tagRepository.findById(id)
                .map(c -> {
                    c.setName(name);
                    return tagRepository.save(c);
                })
                .orElseThrow(() -> new RuntimeException("Tag no encontrado"));
    }

    public void delete(Long id) {
        tagRepository.deleteById(id);
    }
}
