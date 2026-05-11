package com.albavg.rest.repos;

import com.albavg.rest.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByUsername(String username);

    boolean existsByEmail(String email);

}
