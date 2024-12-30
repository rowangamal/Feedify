package com.example.backend.controllers;

import com.example.backend.dtos.UserDTO;
import com.example.backend.dtos.UserSearchDTO;
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
    @GetMapping(value = "/username/{username}", produces = "application/json")
    public List<UserSearchDTO> getUsersByUsername(@PathVariable String username) {
        System.out.println(username);
        return searchService.getUsersByUsername(username);
    }

    // Endpoint to search users by email
    @GetMapping(value = "/email/{email}", produces = "application/json")
    public List<UserSearchDTO> getUsersByEmail(@PathVariable String email) {
        return searchService.getUsersByEmail(email);
    }
}
