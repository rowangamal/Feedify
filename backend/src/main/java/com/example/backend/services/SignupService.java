package com.example.backend.services;

import com.example.backend.dtos.UserSignupDTO;
import com.example.backend.entities.User;
import com.example.backend.exceptions.UserAlreadyExistException;
import com.example.backend.signUp.InvalidEmailHandler;
import com.example.backend.signUp.SignupHandler;
import com.example.backend.signUp.UserAlreadyExistHandler;
import com.example.backend.signUp.UsernameTakenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class SignupService {
    @Autowired
    private UserService userService;

    public void signup(UserSignupDTO userSignupDTO) {
        SignupHandler handlerChain = new InvalidEmailHandler()
                .setNextHandler(new UserAlreadyExistHandler(userService))
                .setNextHandler(new UsernameTakenHandler(userService));

        handlerChain.handleRequest(userSignupDTO);

        try {
            User user = createUserFromDTO(userSignupDTO);
            userService.saveUser(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistException("Email already in use");
        }
    }


    private User createUserFromDTO(UserSignupDTO userSignupDTO){
        User user = new User();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setFName(userSignupDTO.getFirstName());
        user.setLName(userSignupDTO.getLastName());
        user.setUsername(userSignupDTO.getUsername());
        user.setEmail(userSignupDTO.getEmail());
        user.setPassword(encoder.encode(userSignupDTO.getPassword()));
        user.setGender(userSignupDTO.isGender());
        user.setBirthDate(userSignupDTO.getDateOfBirth());
        return user;
    }


    public void signupGoogle(UserSignupDTO userSignupDTO)
            throws NoSuchAlgorithmException {
        userSignupDTO.setUsername(generateUsername(userSignupDTO.getEmail()));
        SignupHandler signupHandler = new UsernameTakenHandler(userService);
        signupHandler.handleRequest(userSignupDTO);

        try {
            User user = createUserFromDTOsignupGoogle(userSignupDTO);
            userService.saveUser(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistException("Email already in use");
        }
    }

    private User createUserFromDTOsignupGoogle(UserSignupDTO userSignupDTO)
            throws NoSuchAlgorithmException{

        User user = new User();
        user.setFName(userSignupDTO.getFirstName());
        user.setLName(userSignupDTO.getLastName());
        user.setUsername(userSignupDTO.getUsername());
        user.setEmail(userSignupDTO.getEmail());
        user.setPassword(null);
        user.setGender(false);
        user.setBirthDate(null);
        return user;
    }

    private static String generateUsername(String email)
            throws NoSuchAlgorithmException {
        String localPart = email.split("@")[0];
        String emailHash = hashEmail(email);
        return "%s_%s".formatted(localPart, emailHash);
    }

    private static String hashEmail(String email)
            throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(email.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}