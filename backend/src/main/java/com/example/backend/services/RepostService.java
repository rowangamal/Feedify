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

    public void repostPost(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Repost repost = new Repost();
        repost.setUser(user);
        repost.setPost(post);
        repostRepository.save(repost);

        post.setRepostsCount(post.getRepostsCount() + 1);
        postRepository.save(post);
    }

    public List<Repost> getAllRepostsByUser(Long userId) {
        return repostRepository.findByUserId(userId);
    }

    public void deleteRepost(Long userId, Long repostId) {
        Repost repost = repostRepository.findByIdAndUserId(repostId, userId)
                .orElseThrow(() -> new RuntimeException("Repost not found or does not belong to the user"));


        Post post = repost.getPost();
        post.setRepostsCount(post.getRepostsCount() - 1);
        postRepository.save(post);

        repostRepository.delete(repost);
    }
}
