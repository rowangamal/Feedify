package com.example.backend.controllers;

import com.example.backend.dtos.UserDTO;
import com.example.backend.services.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    // Endpoint to search users by username
    @GetMapping("/username/{username}")
    public List<UserDTO> getUsersByUsername(@PathVariable String username) {
        return searchService.getUsersByUsername(username);
    }

    // Endpoint to search users by email
    @GetMapping("/email/{email}")
    public List<UserDTO> getUsersByEmail(@PathVariable String email) {
        return searchService.getUsersByEmail(email);
    }
}
