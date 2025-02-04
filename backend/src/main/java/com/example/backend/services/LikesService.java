package com.example.backend.services;

import com.example.backend.entities.Like;
import com.example.backend.entities.User;
import com.example.backend.exceptions.LikeNotFoundException;
import com.example.backend.exceptions.MultipleLikeException;
import com.example.backend.exceptions.PostNoFoundException;
import com.example.backend.notifications.Notification;
import com.example.backend.repositories.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class LikesService {
    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private Notification notification;
    @Autowired
    private PostService postService;

    public void likePost(long postId) {
        long userId = userService.getUserId();
        try {
            likesRepository.addLike(new Timestamp(System.currentTimeMillis()), postId, userId);
            Optional<User> user = userService.getCurrentUser();
            String message = "%s liked your post".formatted(user.get().getUsername());
            notification.sendNotificationLike(message, user.get().getPictureURL(), postService.getPostAuthorId(postId));

        } catch(DataIntegrityViolationException e){
            throw new MultipleLikeException("Post already liked");

        } catch(NullPointerException e){
            throw new PostNoFoundException("Post not found");

        } catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid data format");

        } catch (Exception e){
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

    public void checkIfPostIsLiked(long postId) {
        try{
            long userId = userService.getUserId();
            Optional<Like> like = likesRepository.findLikeByPostIdAndUserId(postId, userId);
            if (like.isEmpty()) {
                throw new LikeNotFoundException("No like found for postId: " + postId + " and userId: " + userId);
            }
        }
        catch(LikeNotFoundException e){
            throw e;
        }
        catch(NullPointerException e){
            throw new PostNoFoundException("Post not found");
        }
        catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid data format");
        }
        catch(Exception e){
            throw new RuntimeException("Unexpected error occurred");
        }
    }
}
