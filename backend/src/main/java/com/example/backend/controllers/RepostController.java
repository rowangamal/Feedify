package com.example.backend.controllers;

import com.example.backend.entities.Repost;
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
    public ResponseEntity<Void> repost(@RequestParam Long userId, @RequestParam Long postId) {
        repostService.repostPost(userId, postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public List<Repost> getAllRepostsByUser(@PathVariable Long userId) {
        return repostService.getAllRepostsByUser(userId);
    }

    @DeleteMapping("/{userId}/{repostId}")
    public void deleteRepost(@PathVariable Long userId, @PathVariable Long repostId) {
        repostService.deleteRepost(userId, repostId);
    }

    @GetMapping("/users/{postId}")
    public List<User> getUsersWhoRepostedPost(@PathVariable Long postId) {
        return repostService.getUsersWhoRepostedPost(postId);
    }
}
