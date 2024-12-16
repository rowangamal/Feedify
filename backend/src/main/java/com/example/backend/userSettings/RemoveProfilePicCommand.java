package com.example.backend.userSettings;

import com.example.backend.entities.User;
import com.example.backend.fileHandling.HandleImage;
import com.example.backend.repositories.UserRepository;

import java.io.IOException;

public class RemoveProfilePicCommand extends UserSettingsCommand {
    private HandleImage handleImage;
    public RemoveProfilePicCommand(User user, UserRepository userRepository) {
        super(user, userRepository);
        handleImage = HandleImage.getInstance();

    }

    @Override
    public String execute() {
        try {
            return this.executeOperation();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
    }
    private String executeOperation() throws IOException {
        if(user.getPictureURL() == null)
            return "No profile picture to remove";
        String parentPath = "../frontend/public/uploads/profile/";
        handleImage.deleteImage(parentPath + user.getPictureURL());
        user.setPictureURL(null);
        userRepository.save(user);
        return "Profile picture removed successfully";
    }
}
