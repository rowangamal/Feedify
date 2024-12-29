package com.example.backend.services;

import com.example.backend.dtos.ChangePasswordDTO;
import com.example.backend.dtos.UserInfoDTO;
import com.example.backend.entities.User;
import com.example.backend.fileHandling.HandleListJson;
import com.example.backend.repositories.UserRepository;
import com.example.backend.userSettings.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserSettingsInfo {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private HandleListJson handleListJson;
    public UserInfoDTO getUserSettings() {
        long userId = userService.getUserId();
        return userRepository.findById(userId).map(user -> new UserInfoDTO(user.getUsername(), user.getPictureURL())).orElse(null);

    }
    @Transactional
    public List<Long> getInterests() {
        long userId = userService.getUserId();
        return userRepository.getAllInterests(userId);
    }

    public String changeUsername(String newUsername){
        User user = getUser();
        ChangeUsernameCommand changeUsernameCommand = new ChangeUsernameCommand(user, userRepository, newUsername);
        InvokeUserSettingsCommand invokeUserSettingsCommand = new InvokeUserSettingsCommand(changeUsernameCommand);
        return invokeUserSettingsCommand.execute();
    }

    public String changePassword(ChangePasswordDTO changePasswordDTO){
        User user = getUser();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        ChangePasswordCommand changePasswordCommand = new ChangePasswordCommand(user, userRepository, changePasswordDTO.newPassword, changePasswordDTO.oldPassword , encoder);
        InvokeUserSettingsCommand invokeUserSettingsCommand = new InvokeUserSettingsCommand(changePasswordCommand);
        return invokeUserSettingsCommand.execute();
    }
    public String changeProfilePic(MultipartFile newProfilePic){
        User user = getUser();
        ChangeProfilePic changeProfilePic = new ChangeProfilePic(user, userRepository, newProfilePic);
        InvokeUserSettingsCommand invokeUserSettingsCommand = new InvokeUserSettingsCommand(changeProfilePic);
        return invokeUserSettingsCommand.execute();
    }
    public String removeProfilePic(){
        User user = getUser();
        RemoveProfilePicCommand removeProfilePicCommand = new RemoveProfilePicCommand(user, userRepository);
        InvokeUserSettingsCommand invokeUserSettingsCommand = new InvokeUserSettingsCommand(removeProfilePicCommand);
        return invokeUserSettingsCommand.execute();
    }
    @Transactional
    public String modifyInterests(String newInterests){
        long userId = userService.getUserId();
        List<Long> interests = (List<Long>) handleListJson.getInterests(newInterests);
        ChangeUserInterestsCommand changeUserInterestsCommand = new ChangeUserInterestsCommand(new User(), userRepository ,interests , userId);
        InvokeUserSettingsCommand invokeUserSettingsCommand = new InvokeUserSettingsCommand(changeUserInterestsCommand);
        return invokeUserSettingsCommand.execute();
    }

    public User getUser(){
        long userId = userService.getUserId();
        return userRepository.findById(userId).orElse(null);
    }
}
