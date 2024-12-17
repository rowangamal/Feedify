package com.example.backend.controllers;



import com.example.backend.dtos.ChangePasswordDTO;
import com.example.backend.dtos.UserInfoDTO;
import com.example.backend.exceptions.InvalidCredentialsException;
import com.example.backend.exceptions.UsernameTakenException;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.UserService;
import com.example.backend.services.UserSettingsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/userSettings")
@CrossOrigin("*")
public class UserSettingsController implements Controller {

    @Autowired
    private UserSettingsInfo userSettingsInfo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    @GetMapping("info")
    public ResponseEntity<UserInfoDTO> getUserInfo(){
        try {
            UserInfoDTO userInfoDTO = userSettingsInfo.getUserSettings() ;
            return ResponseEntity.status(HttpStatus.OK).body(userInfoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserInfoDTO());
        }
    }
    @GetMapping("interests")
    public ResponseEntity<List<Long>> getInterests(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userSettingsInfo.getInterests());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }
    @PostMapping("changeInterests")
    public ResponseEntity<String> changeInterests(@RequestParam(value = "interests")String interestsJson){
        try {

            return ResponseEntity.status(HttpStatus.OK).body(userSettingsInfo.modifyInterests(interestsJson));
        } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error changing interests");
        }
    }
    @PutMapping("changeUsername")
    public ResponseEntity<String> changeUsername(@RequestParam(value = "newUsername") String newUsername){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userSettingsInfo.changeUsername(newUsername));
        } catch (UsernameTakenException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error changing username");
        }
    }
    @PutMapping("changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userSettingsInfo.changePassword(changePasswordDTO));
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error changing password");
        }
    }
    @PutMapping("changeProfilePic")
    public ResponseEntity<String> changeProfilePic(@RequestParam(value = "imageURL")MultipartFile newProfilePic){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userSettingsInfo.changeProfilePic(newProfilePic));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error changing profile picture");
        }
    }
    @DeleteMapping("removeProfilePic")
    public ResponseEntity<String> removeProfilePic(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userSettingsInfo.removeProfilePic());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error removing profile picture");
        }
    }
}
