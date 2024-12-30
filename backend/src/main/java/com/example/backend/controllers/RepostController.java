package com.example.backend.controllers;

import com.example.backend.dtos.RepostRequestDTO;
import com.example.backend.entities.Repost;
import com.example.backend.entities.User;
import com.example.backend.exceptions.RepostNotFoundException;
import com.example.backend.services.RepostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

//    @GetMapping("/")
//    public List<Repost> getAllRepostsByUser() {
//        return repostService.getAllRepostsByUser();
//    }
//
//    @DeleteMapping("/{userId}/reposts/{repostId}")
//    public ResponseEntity<Void> deleteRepost(@PathVariable Long userId, @PathVariable Long repostId) {
//        try {
//            repostService.deleteRepost(userId, repostId);
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//        } catch (RepostNotFoundException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @GetMapping("/users/{postId}")
    public List<User> getUsersWhoRepostedPost(@PathVariable Long postId) {
        return repostService.getUsersWhoRepostedPost(postId);
    }
}
