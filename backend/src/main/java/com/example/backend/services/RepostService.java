package com.example.backend.services;

import com.example.backend.dtos.RepostRequestDTO;
import com.example.backend.dtos.UserSearchDTO;
import com.example.backend.entities.Post;
import com.example.backend.entities.Repost;
import com.example.backend.entities.User;
import com.example.backend.repositories.PostRepository;
import com.example.backend.repositories.RepostRepository;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<UserSearchDTO> getUsersWhoRepostedPost(Long postId) {
        List<Repost> reposts = repostRepository.findByPostId(postId);
        return reposts.stream()
                .map(Repost::getUser)
                .map(user -> new UserSearchDTO(user.getId(), user.getEmail(), user.getUsername())) // Convert User to UserSearchDTO
                .collect(Collectors.toList());
    }
}
