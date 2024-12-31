package com.example.backend.services;

import com.example.backend.dtos.FeedDTO;
import com.example.backend.dtos.PostsResponseDTO;
import com.example.backend.dtos.PostsWrapperDTO;
import com.example.backend.dtos.UserInfoDTO;
import com.example.backend.entities.Post;
import com.example.backend.entities.User;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.repositories.PostRepo;
import com.example.backend.repositories.UserRepo;
import com.example.backend.repositories.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    public PostsWrapperDTO getPersonalProfileFeed(FeedDTO feedDTO) throws Exception {
        try {
            feedDTO.setUserId(userService.getUserId());
            User user = userRepository.findUsersByUsername(feedDTO.getUsername())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            int postsCount = postRepo.getPostsCountByUser(user.getId());
            PostsWrapperDTO postsWrapperDTO = new PostsWrapperDTO();
            postsWrapperDTO.setPosts(postRepo.getPostsByUser(
                    user.getId(),
                    feedDTO.getPageNumber(),
                    feedDTO.getPageSize()));
            postsWrapperDTO.setTotalPages(calculateTotalPages(postsCount, feedDTO.getPageSize()));
            return postsWrapperDTO;
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("Error, User not found");
        }
    }

    public PostsWrapperDTO getFollowingFeed(FeedDTO feedDTO) {
        try {
            feedDTO.setUserId(userService.getUserId());
            List<User> followedUsers = userRepo.getFollowedUsersOfUser(feedDTO.getUserId());
            int postsCount = 0;
            for (User user : followedUsers) {
                postsCount += postRepo.getPostsCountByUser(user.getId());
            }
            PostsWrapperDTO postsWrapperDTO = new PostsWrapperDTO();
            postsWrapperDTO.setPostResponses(postRepo.getPostsOfUsers(
                    followedUsers,
                    feedDTO.getPageNumber(),
                    feedDTO.getPageSize()));
            postsWrapperDTO.setTotalPages(calculateTotalPages(postsCount, feedDTO.getPageSize()));
            return postsWrapperDTO;
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("Error in getting following feed, User not found");
        }
    }

    public PostsWrapperDTO getTopicsFeed(FeedDTO feedDTO) {
        try {
            feedDTO.setUserId(userService.getUserId());
            List<String> topics = userRepo.getUserInterests(feedDTO.getUserId());
            int postsCount = postRepo.getPostsCountByTopic(topics);
            PostsWrapperDTO postsWrapperDTO = new PostsWrapperDTO();
            postsWrapperDTO.setPostResponses(postRepo.getPostAndCreatorByTopics(
                    topics,
                    feedDTO.getPageNumber(),
                    feedDTO.getPageSize()));
            postsWrapperDTO.setTotalPages(calculateTotalPages(postsCount, feedDTO.getPageSize()));
            return postsWrapperDTO;
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("Error in getting topics feed, User not found");
        }
    }

    public PostsWrapperDTO getVisitedProfileFeed(FeedDTO feedDTO) {
        try {
            User user = userRepository.findUsersByUsername(feedDTO.getUsername())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            int postsCount = postRepo.getPostsCountByUser(user.getId());
            PostsWrapperDTO postsWrapperDTO = new PostsWrapperDTO();
            postsWrapperDTO.setPostResponses(postRepo.getPostsOfUsers(
                    List.of(user),
                    feedDTO.getPageNumber(),
                    feedDTO.getPageSize()));
            postsWrapperDTO.setTotalPages(calculateTotalPages(postsCount, feedDTO.getPageSize()));
            return postsWrapperDTO;
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("Error in getting this user profile feed, User not found");
        }
    }

    public UserInfoDTO getUser(FeedDTO feedDTO) throws Exception {
        try {
            User user = userRepository.findUsersByUsername(feedDTO.getUsername())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            return new UserInfoDTO(user.getUsername(), user.getPictureURL());
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("Error in getting this user, User not found");
        }
    }

    public Integer getTotalPages(FeedDTO feedDTO) {
        try {
            feedDTO.setUserId(userService.getUserId());
            int postsCount = postRepo.getPostsCountByUser(feedDTO.getUserId());
            return calculateTotalPages(postsCount, feedDTO.getPageSize());
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("Error in getting total pages, User not found");
        }
    }

    private int calculateTotalPages(int postsCount, int pageSize) {
        int totalPages = postsCount / pageSize;
        if(postsCount % pageSize == 0) {
            return totalPages;
        }
        return (postsCount / pageSize) + 1;
    }
}
