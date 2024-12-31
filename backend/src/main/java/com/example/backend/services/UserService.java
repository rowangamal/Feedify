package com.example.backend.services;

import com.example.backend.dtos.FollowingDTO;
import com.example.backend.entities.Admin;
import com.example.backend.entities.User;
import com.example.backend.entities.UserDetail;
import com.example.backend.enums.Role;
import com.example.backend.exceptions.UnauthorizedAccessException;
import com.example.backend.exceptions.UserAlreadyFollowedException;
import com.example.backend.exceptions.UserAlreadyUnfollowedException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.notifications.Notification;
import com.example.backend.repositories.AdminRepository;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private JWTBlacklistService jwtBlacklistService;

    @Autowired
    private Notification notification;

    public User getUserByEmail(String email){
        if (email == null || email.isEmpty()) {
            throw new NullPointerException("Email cannot be empty");
        }
        return userRepository.findUsersByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("Incorrect email or password"));
    }

    public User getUserByUsername(String username){
        if (username == null || username.isEmpty()) {
            throw new NullPointerException("Username cannot be empty");
        }
        return userRepository.findUsersByUsername(username)
                .orElseThrow(()-> new UserNotFoundException("Incorrect email or password"));
    }

    public boolean isAdmin(User user){
        Admin admin = adminRepository.findByUser(user);
        return admin != null;
    }

    public User getUserById(long id){
        return userRepository.findUserById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
    }

    public Role getUserRole(User user){
        if(isAdmin(user)){
            return Role.ADMIN;
        }
        return Role.USER;
    }

    public long getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        /*
         * If the user is not authenticated or the principal is not an instance of UserDetail, throw an UnauthorizedAccessException
         * This case should not happen, because it means caller expects authenticated user to be present
         * We would not have reached this point if the user was not authenticated
         * but for security reasons, we should check this case
         */
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetail)) {
            throw new UnauthorizedAccessException("User is not authenticated or invalid principal");
        }
        return  ((UserDetail)authentication.getPrincipal()).getUserId();
    }

    Optional<User> getCurrentUser() {
        return userRepository.findUserById(getUserId());
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }

    public void followUser(Long followingId) {
        Optional<User> currentUser = getCurrentUser();
        Optional<User> following = userRepository.findUserById(followingId);

        if (currentUser.isPresent() && following.isPresent()) {
            User user = currentUser.get();
            User followingUser = following.get();

            if(isUserFollowed(user, followingUser)) throw new UserAlreadyFollowedException();

            user.getFollowing().add(followingUser);
            followingUser.getFollowers().add(user);

            userRepository.save(followingUser);
            userRepository.save(user);

            String message = "%s followed you".formatted(user.getUsername());
            notification.sendNotificationFollow(message, user.getPictureURL(), followingUser.getId());
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public void unfollowUser(Long followingId) {
        Optional<User> currentUser = getCurrentUser();
        Optional<User> following = userRepository.findById(followingId);

        if (currentUser.isPresent() && following.isPresent()) {
            User user = currentUser.get();
            User followingUser = following.get();

            if(!isUserFollowed(user, followingUser)) throw new UserAlreadyUnfollowedException();

            user.getFollowing().remove(followingUser);
            followingUser.getFollowers().remove(user);

            userRepository.save(user);
            userRepository.save(followingUser);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    private Boolean isUserFollowed(User user, User followingUser) {
        return user.getFollowing().contains(followingUser);
    }

    public void isUserFollowed(long followingUser) {
        User following = getUserById(followingUser);
        Optional<User> user = getCurrentUser();
        if (user.isPresent()) {
            if(!user.get().getFollowing().contains(following)) throw new UserNotFoundException("User not found");
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public List<FollowingDTO> getFollowing() {
        Optional<User> user = getCurrentUser();
        if (user.isPresent()) {
            List<FollowingDTO> followingDTOS = user.get().getFollowing().stream().map(following -> new FollowingDTO(following.getId(), following.getUsername())).toList();
            return followingDTOS;
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public List<FollowingDTO> getFollowers() {
        Optional<User> user = getCurrentUser();
        if (user.isPresent()) {
            List<FollowingDTO> followersDTOS = user.get().getFollowers().stream().map(following -> new FollowingDTO(following.getId(), following.getUsername())).toList();

            return followersDTOS;
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public long getFollowersCount() {
        return getCurrentUser()
                .map(user -> (long) user.getFollowers().size())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public long getFollowingCount() {
        return getCurrentUser()
                .map(user -> (long) user.getFollowing().size())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public List<FollowingDTO> getFollowersOfUser(String username){
        Optional<User> user = userRepository.findUsersByUsername(username);
        if(user.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        return user.get().getFollowers()
                .stream()
                .map(follower -> new FollowingDTO(
                        follower.getId(),
                        follower.getUsername()))
                .collect(Collectors.toList());
    }

    public List<FollowingDTO> getFollowingOfUser(String username){
        Optional<User> user = userRepository.findUsersByUsername(username);
        if(user.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        return user.get().getFollowing()
                .stream()
                .map(following -> new FollowingDTO(
                        following.getId(),
                        following.getUsername()))
                .collect(Collectors.toList());
    }

    public Long getFollowersCountOfUser(String username){
        Optional<User> user = userRepository.findUsersByUsername(username);
        if(user.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        return (long) user.get().getFollowers().size();
    }

    public Long getFollowingCountOfUser(String username){
        Optional<User> user = userRepository.findUsersByUsername(username);
        if(user.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        return (long) user.get().getFollowing().size();
    }

    public void logout(String authHeader){
        String token;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        } else {
            throw new UnauthorizedAccessException("No authorization header found");
        }
        jwtBlacklistService.BlacklistToken(token);
    }
}