package com.example.backend.UserSettings;

import com.example.backend.entities.User;
import com.example.backend.exceptions.UsernameTakenException;
import com.example.backend.repositories.UserRepository;
import com.example.backend.userSettings.ChangeUsernameCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class ChangeUsernameTest {
    @InjectMocks
    private ChangeUsernameCommand changeUsernameCommand;

    @Mock
    private UserRepository userRepository;

    private AutoCloseable mocks;
    @BeforeEach
    void setUp() {
        mocks = openMocks(this);
    }

    @Test
    void testChangeUsernameWithExistingOne() {
        when(userRepository.findUsersByUsername("rafy")).thenReturn(Optional.of(new User()));
        when(userRepository.save(new User())).thenReturn(new User());
        changeUsernameCommand = new ChangeUsernameCommand(new User(), userRepository, "rafy");
        assertThrows( UsernameTakenException.class , changeUsernameCommand::execute);

    }
    @Test
    void testChangeUsernameWhichIsUnique() {
        when(userRepository.findUsersByUsername("rafy")).thenReturn(Optional.empty());
        when(userRepository.save(new User())).thenReturn(new User());
        changeUsernameCommand = new ChangeUsernameCommand(new User(), userRepository, "rafy");
        assertEquals("rafy", changeUsernameCommand.execute());

    }


    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }
}
