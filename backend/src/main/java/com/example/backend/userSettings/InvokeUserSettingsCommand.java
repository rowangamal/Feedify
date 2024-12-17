package com.example.backend.userSettings;

import com.example.backend.postInteractions.PostCommand;
import org.springframework.transaction.annotation.Transactional;

public class InvokeUserSettingsCommand {
    private final UserSettingsCommand userSettingsCommand;

    public InvokeUserSettingsCommand(UserSettingsCommand userSettingsCommand ) {
        this.userSettingsCommand = userSettingsCommand;
    }

    @Transactional
    public String execute() {
        return userSettingsCommand.execute();
    }
}
