package com.albavg.rest.repos;

import com.albavg.rest.model.Tag;
import com.albavg.rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByOwner(User owner);
    Optional<Tag> findByIdAndOwner(Long id, User owner);
    List<Tag> findAllByIdInAndOwner(List<Long> ids, User owner);
}
