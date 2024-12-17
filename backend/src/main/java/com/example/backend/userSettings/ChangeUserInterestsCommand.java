package com.example.backend.userSettings;

import com.example.backend.entities.User;
import com.example.backend.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ChangeUserInterestsCommand extends UserSettingsCommand {
    private final List<Long> newInterests;
    private final long userId;

    public ChangeUserInterestsCommand(User user, UserRepository userRepository, List<Long> newInterests , long userId) {
        super(user, userRepository);
        this.newInterests = newInterests;
        this.userId = userId;
    }

    @Override
    @Transactional
    public String execute() {
        userRepository.removeInterestsBatch(this.userId);
        for(long interest : newInterests)
            userRepository.addInterest(userId, interest);
        return "Interests changed successfully";
    }
}
