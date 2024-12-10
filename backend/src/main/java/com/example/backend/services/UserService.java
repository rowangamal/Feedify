package com.example.backend.services;
import com.example.backend.entities.Admin;
import com.example.backend.entities.User;
import com.example.backend.entities.UserDetail;
import com.example.backend.enums.Role;
import com.example.backend.exceptions.UnauthorizedAccessException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.repositories.AdminRepository;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import com.example.backend.entities.User;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

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


    public void saveUser(User user){
        userRepository.save(user);
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(newPassword); // TODO need to use password encoder
        userRepository.save(user);
    }
}

