package com.albavg.rest.service;

import com.albavg.rest.model.Tag;
import com.albavg.rest.model.User;
import com.albavg.rest.repos.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> findAll(User owner) {
        return tagRepository.findByOwner(owner);
    }

    public Tag findById(Long id, User owner) {
        return tagRepository.findByIdAndOwner(id, owner)
                .orElseThrow(() -> new RuntimeException("Tag no encontrado"));
    }

    public Tag save(String name, User owner) {
        return tagRepository.save(
                Tag.builder().name(name).owner(owner).build()
        );
    }

    public Tag edit(Long id, String name, User owner) {
        return tagRepository.findByIdAndOwner(id, owner)
                .map(c -> {
                    c.setName(name);
                    return tagRepository.save(c);
                })
                .orElseThrow(() -> new RuntimeException("Tag no encontrado"));
    }

    public void delete(Long id, User owner) {
        Tag tag = tagRepository.findByIdAndOwner(id, owner)
                .orElseThrow(() -> new RuntimeException("Tag no encontrado"));
        tagRepository.delete(tag);
    }
}
