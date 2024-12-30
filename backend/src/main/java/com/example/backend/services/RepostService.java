package com.example.backend.services;

import com.example.backend.entities.Post;
import com.example.backend.entities.Repost;
import com.example.backend.entities.User;
import com.example.backend.repositories.PostRepository;
import com.example.backend.repositories.RepostRepository;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepostService {
    @Autowired
    private RepostRepository repostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;


    @Autowired
    private UserService userService;

    public void repostPost(Long postId) {
        User user = userRepository.findById(userService.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: %d".formatted(userService.getUserId())));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with ID: %d".formatted(postId)));

        if (repostRepository.existsByPostIdAndUserId(postId, userService.getUserId())) {
            throw new RuntimeException("User has already reposted this post");
        }

        Repost repost = new Repost();
        repost.setUser(user);
        repost.setPost(post);
        repostRepository.save(repost);

        post.setRepostsCount(post.getRepostsCount() + 1);
        postRepository.save(post);

    }

    public List<User> getUsersWhoRepostedPost(Long postId) {
        return repostRepository.findUsersByPostId(postId);
    }

    public List<Repost> getAllRepostsByUser(Long userId) {
        return repostRepository.findByUserId(userId);
    }

    public void deleteRepost(Long userId, Long repostId) {
        Repost repost = repostRepository.findByIdAndUserId(repostId, userId)
                .orElseThrow(() -> new RuntimeException("Repost not found or does not belong to the user"));

        Post post = repost.getPost();

        if (post == null) {
            throw new RuntimeException("Post not found for the repost");
        }
        post.setRepostsCount(post.getRepostsCount() - 1);
        postRepository.save(post);
        repostRepository.delete(repost);
    }
}
