package com.example.backend.controllers;

import com.example.backend.dtos.LikeDTO;
import com.example.backend.exceptions.PostNoFoundException;
import com.example.backend.services.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class LikesController {
    @Autowired
    private LikesService likesService;

    @PostMapping
    public ResponseEntity<Object> likePost(@RequestBody LikeDTO likeDTO){
        try{
            likesService.likePost(likeDTO.getPostId());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Post liked");
    }

    @DeleteMapping
    public ResponseEntity<Object> unlikePost(@RequestBody LikeDTO likeDTO){
        try{
            likesService.unlikePost(likeDTO.getPostId());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Post unliked");
    }
}
