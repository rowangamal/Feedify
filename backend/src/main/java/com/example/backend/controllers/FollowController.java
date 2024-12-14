package com.example.backend.controllers;

import com.example.backend.dtos.FollowDTO;
import com.example.backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.services.UserService;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class FollowController {
    @Autowired
    private UserService userService;

    @PostMapping("/follow")
    public ResponseEntity<String> followUser(@RequestBody FollowDTO followDTO) {
        try {
            userService.followUser(followDTO.getFollowId());
            return ResponseEntity.ok("Followed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // TODO : handle if user enters follow multiple times / unfollow too

    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestBody FollowDTO followDTO) {
        try {
            userService.unfollowUser(followDTO.getFollowId());
            return ResponseEntity.ok("Unfollowed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/following")
    public ResponseEntity<List<User>> getFollowing() {
        try {
            List<User> following = userService.getFollowing();
            return ResponseEntity.ok(following);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/followers")
    public ResponseEntity<List<User>> getFollowers() {
        try {
            List<User> followers = userService.getFollowers();
            return ResponseEntity.ok(followers);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}