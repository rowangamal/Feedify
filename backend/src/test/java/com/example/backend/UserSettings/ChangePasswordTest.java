package com.example.backend.UserSettings;

import com.example.backend.entities.User;
import com.example.backend.exceptions.InvalidCredentialsException;
import com.example.backend.exceptions.UsernameTakenException;
import com.example.backend.repositories.UserRepository;
import com.example.backend.userSettings.ChangePasswordCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class ChangePasswordTest {
    @InjectMocks
    private ChangePasswordCommand changePasswordCommand;

    @Mock
    private UserRepository userRepository;

    private AutoCloseable mocks;
    @BeforeEach
    void setUp() {
        mocks = openMocks(this);
    }

    @Test
    void testChangePasswordWithGoogleUser() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        when(userRepository.save(new User())).thenReturn(new User());
        changePasswordCommand = new ChangePasswordCommand(new User(),userRepository, "password", "oldPassword", encoder);
        assertThrows( NullPointerException.class , changePasswordCommand::execute);
    }
    @Test
    void testChangePasswordWithCorrectOldPassword() {
        //given
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        User user = new User();
        String oldPassword = encoder.encode("oldPassword");
        user.setPassword(oldPassword);
        //when
        when(userRepository.save(new User())).thenReturn(new User());
        //then
        changePasswordCommand = new ChangePasswordCommand(user,userRepository, "password", "oldPassword", encoder);
        assertEquals("Password changed successfully" , changePasswordCommand.execute());
    }
    @Test
    void testChangePasswordWithInCorrectOldPassword() {
        //given
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        User user = new User();
        String oldPassword = encoder.encode("oldPassword");
        user.setPassword(oldPassword);
        //when
        when(userRepository.save(new User())).thenReturn(new User());
        //then
        changePasswordCommand = new ChangePasswordCommand(user,userRepository, "password", "inCorrectPassword", encoder);
        assertThrows(InvalidCredentialsException.class , changePasswordCommand::execute);
    }


    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

}
