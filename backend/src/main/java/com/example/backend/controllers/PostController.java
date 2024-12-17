package com.example.backend.controllers;

import com.example.backend.dtos.PostDTO;
import com.example.backend.entities.PostType;
import com.example.backend.exceptions.PostOutOfLimitException;
import com.example.backend.exceptions.PostWithNoType;
import com.example.backend.exceptions.PostWithZeroContent;
import com.example.backend.services.PostService;
import com.example.backend.services.PostTypesService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/post")
@CrossOrigin("*")
public class PostController implements Controller {
    @Autowired
    private PostService postService;

    @Autowired
    private PostTypesService postTypesService;
    @PostMapping("createPost")
    public ResponseEntity<String> createPost(
            @RequestParam(value = "imageURL", required = false) MultipartFile image,
            @RequestParam("post") String postDTO){
        try {
            postService.createPost(postDTO, image);
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
        } catch (PostOutOfLimitException | PostWithNoType | PostWithZeroContent | NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating post");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("server error");
        }
    }
    @GetMapping("getTypes")
    public ResponseEntity<List<PostType>> getAllTypes(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(postTypesService.getPostTypes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }
}
