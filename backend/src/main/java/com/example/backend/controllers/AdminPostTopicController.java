package com.example.backend.controllers;

import com.example.backend.dtos.TopicRequest;
import com.example.backend.services.PostTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/postTopic")
public class AdminPostTopicController {
    @Autowired
    private PostTypesService postTypesService;

    @GetMapping
    public ResponseEntity<Object> getAllPostTopics() {
        try {
            return ResponseEntity.ok().body(postTypesService.getAllTopics());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addPostTopic(@RequestBody TopicRequest topic) {
        try {
            postTypesService.addTopic(topic.getTopic());
            return ResponseEntity.status(HttpStatus.CREATED).body("Post type added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{postTypeId}")
    public ResponseEntity<Object> deletePostTopic(@PathVariable long postTypeId) {
        try {
            postTypesService.deleteTopic(postTypeId);
            return ResponseEntity.ok().body("Post type deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
