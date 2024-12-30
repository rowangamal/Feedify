package com.example.backend.services;

import com.example.backend.exceptions.LikeNotFoundException;
import com.example.backend.exceptions.MultipleLikeException;
import com.example.backend.exceptions.PostNoFoundException;
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
                throw new MultipleLikeException("Post already liked");
            if(e instanceof NullPointerException)
                throw new PostNoFoundException("Post not found");
            if(e instanceof IllegalArgumentException)
                throw new IllegalArgumentException("Invalid data format");
            throw new RuntimeException("Unexpected error occurred");
        }
    }

    public void unlikePost(long postId) {
        long userId = userService.getUserId();
        try {
            int rowsAffected = likesRepository.removeLike(postId, userId);
            if (rowsAffected == 0)
                throw new LikeNotFoundException("No like found for postId: " + postId + " and userId: " + userId);
        }
        catch(LikeNotFoundException e) {
            throw e;
        }
        catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Invalid data format", e);
        }
        catch (NullPointerException e) {
            throw new NullPointerException("Post not found");
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid data format", e);
        }
        catch (Exception e) {
            System.out.println("Unexpected error occurred during unlikePost: "+ e);
            throw new RuntimeException("Unexpected error occurred");
        }
    }
}
