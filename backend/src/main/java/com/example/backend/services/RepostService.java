package com.example.backend.services;

import com.example.backend.dtos.InteractionsDTO;
import com.example.backend.entities.Post;
import com.example.backend.entities.Repost;
import com.example.backend.entities.User;
import com.example.backend.exceptions.ResourceNotFoundException;
import com.example.backend.exceptions.AlreadyRepostedException;
import com.example.backend.notifications.Notification;
import com.example.backend.repositories.PostRepository;
import com.example.backend.repositories.RepostRepository;
import com.example.backend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepostService {

    private static final Logger logger = LoggerFactory.getLogger(RepostService.class);

    @Autowired
    private RepostRepository repostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private Notification notification;

    public void repostPost(Long postId) {
        Long userId = userService.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: %d".formatted(userId)));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: %d".formatted(postId)));

        if (repostRepository.existsByPostIdAndUserId(postId, userId)) {
            throw new AlreadyRepostedException("User has already reposted this post");
        }

        Repost repost = new Repost();
        repost.setUser(user);
        repost.setPost(post);
        repostRepository.save(repost);
        post.setRepostsCount(post.getRepostsCount() + 1);
        postRepository.save(post);

        String message = "%s reposted your post".formatted(user.getUsername());
        notification.sendNotificationRepost(message, user.getPictureURL(), post.getUser().getId());
    }

    public List<InteractionsDTO> getUsersWhoRepostedPost(Long postId) {
        List<Repost> reposts = repostRepository.findByPostId(postId);
        return reposts.stream()
                .map(Repost::getUser)
                .map(user -> new InteractionsDTO(user.getId(), user.getEmail(), user.getUsername())) // Convert User to UserSearchDTO
                .collect(Collectors.toList());
    }
}
