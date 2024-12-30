package com.example.backend.controllers;

import com.example.backend.dtos.RepostRequestDTO;
import com.example.backend.dtos.UserSearchDTO;
import com.example.backend.entities.User;
import com.example.backend.services.RepostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reposts")
public class RepostController {

    @Autowired
    private RepostService repostService;

    @PostMapping("/repost")
    public ResponseEntity<Void> repost(@RequestBody RepostRequestDTO request) {
        repostService.repostPost(request.getPostId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users")
    public List<UserSearchDTO> getUsersWhoRepostedPost(@RequestBody RepostRequestDTO request) {
        System.out.println(request.getPostId());
        return repostService.getUsersWhoRepostedPost(request.getPostId());
    }
}
