package com.example.backend.controllers;

import com.example.backend.dtos.FollowDTO;
import com.example.backend.dtos.FollowingDTO;
import com.example.backend.exceptions.UserAlreadyFollowedException;
import com.example.backend.exceptions.UserAlreadyUnfollowedException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/remove-follower")
    public ResponseEntity<String> removeFollower(@RequestBody FollowDTO followDTO) {
        try {
            userService.removeFollower(followDTO.getFollowId());
            return ResponseEntity.ok("follower removed successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/following")
    public ResponseEntity<List<FollowingDTO>> getFollowing(@RequestBody FollowingDTO followingDTO) {
        try {
            List<FollowingDTO> following = userService.getFollowingOfUser(followingDTO.getUsername());
            return ResponseEntity.ok(following);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/followers")
    public ResponseEntity<List<FollowingDTO>> getFollowers(@RequestBody FollowingDTO followingDTO) {
        try {
            List<FollowingDTO> followers = userService.getFollowersOfUser(followingDTO.getUsername());
            return ResponseEntity.ok(followers);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/following-count")
    public ResponseEntity<Long> getFollowingCount(@RequestBody FollowingDTO followingDTO) {
        long count = userService.getFollowingCountOfUser(followingDTO.getUsername());
        return ResponseEntity.ok(count);
    }

    @PostMapping("/follower-count")
    public ResponseEntity<Long> getFollowersCount(@RequestBody FollowingDTO followingDTO) {
        long count = userService.getFollowersCountOfUser(followingDTO.getUsername());
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