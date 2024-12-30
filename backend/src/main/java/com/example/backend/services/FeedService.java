package com.example.backend.services;

import com.example.backend.dtos.FeedDTO;
import com.example.backend.dtos.PostsResponseDTO;
import com.example.backend.dtos.UserInfoDTO;
import com.example.backend.entities.Post;
import com.example.backend.entities.User;
import com.example.backend.repositories.PostRepo;
import com.example.backend.repositories.UserRepo;
import com.example.backend.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    private UserRepository userRepository;

    public List<Post> getPersonalProfileFeed(FeedDTO feedDTO) throws Exception {
        try {
             feedDTO.setUserId(userService.getUserId());
             User user = userRepository.findUsersByUsername(feedDTO.getUsername())
                    .orElseThrow(() -> new Exception("User not found"));

            return postRepo.getPostsByUser(user.getId());
        }
        catch (Exception e) {
            throw new UserNotFoundException("Error, User not found");
        }
    }

    public List<PostsResponseDTO> getFollowingFeed(FeedDTO feedDTO) throws Exception {
        try {
            feedDTO.setUserId(userService.getUserId());
            List<User> followedUsers = userRepo.getFollowedUsersOfUser(feedDTO.getUserId());
            return postRepo.getPostsOfUsers(followedUsers);
        }
        catch (Exception e) {
            throw new UserNotFoundException("Error in getting following feed, User not found");
        }
    }

    public List<PostsResponseDTO> getTopicsFeed(FeedDTO feedDTO) throws Exception {
        try {
            feedDTO.setUserId(userService.getUserId());
            List<String> topics = userRepo.getUserInterests(feedDTO.getUserId());
            return postRepo.getPostAndCreatorByTopics(topics);
        }
        catch (Exception e) {
            throw new UserNotFoundException("Error in getting topics feed, User not found");
        }
    }

    public List<PostsResponseDTO> getVisitedProfileFeed(FeedDTO feedDTO) throws Exception {
        try {
            User user = userRepository.findUsersByUsername(feedDTO.getUsername())
                    .orElseThrow(() -> new Exception("User not found"));
            return postRepo.getPostsOfUsers(List.of(user));
        }
        catch (Exception e) {
            throw new UserNotFoundException("Error in getting this user profile feed, User not found");
        }
    }

    public UserInfoDTO getUser(FeedDTO feedDTO) throws Exception {
        try {
            User user = userRepository.findUsersByUsername(feedDTO.getUsername())
                    .orElseThrow(() -> new Exception("User not found"));
            return new UserInfoDTO(user.getUsername(), user.getPictureURL());
        }
        catch (Exception e) {
            throw new UserNotFoundException("Error in getting this user, User not found");
        }
    }
}
