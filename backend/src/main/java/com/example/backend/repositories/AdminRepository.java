package com.example.backend.repositories;

import com.example.backend.entities.Admin;
import com.example.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByUser(User user);
}
