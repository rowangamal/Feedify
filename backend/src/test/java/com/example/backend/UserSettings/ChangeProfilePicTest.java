package com.example.backend.UserSettings;

import com.example.backend.entities.User;
import com.example.backend.repositories.UserRepository;
import com.example.backend.userSettings.ChangeProfilePic;
import com.example.backend.userSettings.RemoveProfilePicCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class ChangeProfilePicTest {
    @InjectMocks
    private ChangeProfilePic changeProfilePicCommand;

    @InjectMocks
    private RemoveProfilePicCommand removeProfilePicCommand;

    @Mock
    private UserRepository userRepository;

    private AutoCloseable mocks;
    @BeforeEach
    void setUp() {
        mocks = openMocks(this);
    }

    @Test
    void changeProfilePicSuccessfullyTest() {
        //given
        MultipartFile newProfilePic = new MockMultipartFile("image", "rafy.jpg", "image/jpg", "rafy.jpg".getBytes());
        User user = new User();

        //when
        when(userRepository.save(new User())).thenReturn(user);

        //then
        changeProfilePicCommand = new ChangeProfilePic(user,userRepository, newProfilePic);
        assertNotNull(changeProfilePicCommand.execute());
    }
    @Test
    void removeExistingProfilePicTest() {
        //given
        User user = new User();
        user.setPictureURL("rafy.jpg");
        User user1 = new User();
        //when
        when(userRepository.save(any(User.class))).thenReturn(user1);
        //then
        removeProfilePicCommand = new RemoveProfilePicCommand(user , userRepository);
        assertEquals("Profile picture removed successfully" ,removeProfilePicCommand.execute());
    }
    @Test
    void removeInExistingProfilePicTest() {
        //given
        User user = new User();

        User user1 = new User();
        //when
        when(userRepository.save(any(User.class))).thenReturn(user1);
        //then
        removeProfilePicCommand = new RemoveProfilePicCommand(user , userRepository);
        assertEquals("No profile picture to remove" ,removeProfilePicCommand.execute());
    }


    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

}
