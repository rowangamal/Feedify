package com.example.backend.controllers;

import com.example.backend.dtos.LikeDTO;
import com.example.backend.exceptions.LikeNotFoundException;
import com.example.backend.exceptions.MultipleLikeException;
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
            return mapExceptionToResponse(e);
        }
        return ResponseEntity.ok().body("Post liked");
    }

    private static ResponseEntity<Object> mapExceptionToResponse(Exception e) {
        if(e instanceof MultipleLikeException)
            return ResponseEntity.status(409).body(e.getMessage());
        if(e instanceof PostNoFoundException || e instanceof LikeNotFoundException)
            return ResponseEntity.status(404).body(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @DeleteMapping
    public ResponseEntity<Object> unlikePost(@RequestBody LikeDTO likeDTO){
        try{
            likesService.unlikePost(likeDTO.getPostId());
        }
        catch (Exception e){
            return mapExceptionToResponse(e);
        }
        return ResponseEntity.ok().body("Post unliked");
    }

    @GetMapping
    public ResponseEntity<Object> isLiked(@RequestParam(value = "postId") long postId){
        try{
            likesService.checkIfPostIsLiked(postId);
            return ResponseEntity.ok().body("Post is liked");
        }
        catch (Exception e){
            return mapExceptionToResponse(e);
        }
    }
}
