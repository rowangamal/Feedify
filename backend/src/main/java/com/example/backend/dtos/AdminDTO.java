package com.example.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminDTO {
    private Long id;
    private String email;

    public AdminDTO(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}