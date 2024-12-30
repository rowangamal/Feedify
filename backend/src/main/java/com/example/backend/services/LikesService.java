package com.example.backend.services;

import com.example.backend.repositories.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class LikesService {
    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private UserService userService;

    public void likePost(long postId){
        long userId = userService.getUserId();
        try{
            likesRepository.addLike(new Timestamp(System.currentTimeMillis()), postId, userId);
        }
        catch (Exception e){
            if(e instanceof DataIntegrityViolationException)
                throw new DataIntegrityViolationException("Invalid data format");
            if(e instanceof NullPointerException)
                throw new NullPointerException("Post not found");
            if(e instanceof IllegalArgumentException)
                throw new IllegalArgumentException("Invalid data format");
            throw new RuntimeException("Unexpected error occurred");
        }
    }

    public void unlikePost(long postId){
        long userId = userService.getUserId();
        try{
            likesRepository.removeLike(postId, userId);
        }
        catch (Exception e){
            if(e instanceof DataIntegrityViolationException)
                throw new DataIntegrityViolationException("Invalid data format");
            if(e instanceof NullPointerException)
                throw new NullPointerException("Post not found");
            if(e instanceof IllegalArgumentException)
                throw new IllegalArgumentException("Invalid data format");
            throw new RuntimeException("Unexpected error occurred");
        }
    }
}
