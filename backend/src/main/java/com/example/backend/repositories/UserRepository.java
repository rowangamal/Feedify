package com.example.backend.repositories;

import com.example.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(long id);
    Optional<User> findUsersByEmail(String email);

    Optional<User> findUsersByUsername(String username);
}
