package com.example.backend.controllers;



import com.example.backend.dtos.UserInfoDTO;
import com.example.backend.services.UserSettingsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userSettings")
@CrossOrigin("*")
public class UserSettingsController implements Controller {

    @Autowired
    private UserSettingsInfo userSettingsInfo;
    @GetMapping("info")
    public ResponseEntity<UserInfoDTO> createPost(){
        try {
            UserInfoDTO userInfoDTO = userSettingsInfo.getUserSettings() ;
            return ResponseEntity.status(HttpStatus.OK).body(userInfoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserInfoDTO());
        }
    }
}
