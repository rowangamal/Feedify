package com.example.backend.controllers;

import com.example.backend.dtos.FollowDTO;
import com.example.backend.entities.User;
import com.example.backend.exceptions.UserAlreadyFollowedException;
import com.example.backend.exceptions.UserAlreadyUnfollowedException;
import com.example.backend.exceptions.UserNotFoundException;
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
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UserAlreadyFollowedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestBody FollowDTO followDTO) {
        try {
            userService.unfollowUser(followDTO.getFollowId());
            return ResponseEntity.ok("Unfollowed successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UserAlreadyUnfollowedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
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

    @GetMapping("/following-count")
    public ResponseEntity<Long> getFollowingCount() {
        long count = userService.getFollowingCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/follower-count")
    public ResponseEntity<Long> getFollowersCount() {
        long count = userService.getFollowersCount();
        return ResponseEntity.ok(count);
    }

    @PostMapping("/is-followed")
    public ResponseEntity<String> isUserFollowed(@RequestBody FollowDTO followDTO) {
        try {
            userService.isUserFollowed(followDTO.getFollowId());
            return ResponseEntity.ok("User is followed");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not followed");
        }
    }
}