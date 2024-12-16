package com.example.backend.services;

import com.example.backend.dtos.FeedDTO;
import com.example.backend.dtos.PostsResponseDTO;
import com.example.backend.entities.Post;
import com.example.backend.entities.User;
import com.example.backend.repositories.PostRepo;
import com.example.backend.repositories.UserRepo;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService implements IService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;

    public List<Post> getProfileFeed(FeedDTO feedDTO) {
        feedDTO.setUserId(userService.getUserId());
        return postRepo.getPostsByUser(feedDTO.getUserId());
    }

    public List<PostsResponseDTO> getFollowingFeed(FeedDTO feedDTO) {
        feedDTO.setUserId(userService.getUserId());
        List<User> followedUsers = userRepo.getFollowedUsersOfUser(feedDTO.getUserId());
        return postRepo.getPostsOfUsers(followedUsers);
    }

    public List<PostsResponseDTO> getTopicsFeed(FeedDTO feedDTO) {
        feedDTO.setUserId(userService.getUserId());
        List<String> topics = userRepo.getUserInterests(feedDTO.getUserId());
        return postRepo.getPostAndCreatorByTopics(topics);
    }
}
