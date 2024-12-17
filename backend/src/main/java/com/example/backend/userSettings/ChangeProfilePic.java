package com.example.backend.userSettings;

import com.example.backend.entities.User;
import com.example.backend.fileHandling.HandleImage;
import com.example.backend.repositories.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ChangeProfilePic extends UserSettingsCommand {
    private final MultipartFile newProfilePic;
    private HandleImage handleImage;

    public ChangeProfilePic(User user, UserRepository userRepository, MultipartFile newProfilePic) {
        super(user, userRepository);
        this.newProfilePic = newProfilePic;
        this.handleImage = HandleImage.getInstance() ;
    }

    @Override
    public String execute() {
        try {
            return this.executeOperation();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }
    private String executeOperation() throws IOException {
        String parentPath = "../frontend/public/uploads/profile/";
        if(user.getPictureURL() != null)
            handleImage.deleteImage(parentPath + user.getPictureURL());
        String newProfilePicPath = handleImage.saveImage(this.newProfilePic, parentPath);
        user.setPictureURL(newProfilePicPath);
        userRepository.save(user);
        return newProfilePicPath;
    }

}
