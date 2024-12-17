package com.example.backend.UserSettings;

import com.example.backend.entities.User;
import com.example.backend.repositories.UserRepository;
import com.example.backend.userSettings.ChangeUserInterestsCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class ChangeInterestsTest {
    private ChangeUserInterestsCommand changeInterestsCommand;
    @Mock
    private UserRepository userRepository;
    private AutoCloseable mocks;
    @BeforeEach
    void setUp() {
        mocks = openMocks(this);
    }

    @Test
    void changeInterestsTest() {

        User user = new User();
        user.setId(1L);
        List<Long> interests = new ArrayList<>();
        interests.add(1L);
        interests.add(2L);

        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);
        changeInterestsCommand = new ChangeUserInterestsCommand(user, userRepository, interests, 1L);
        changeInterestsCommand.execute();

        verify(userRepository, times(1)).removeInterestsBatch(1L);
        verify(userRepository, times(2)).addInterest(eq(1L), anyLong());
    }
    @AfterEach
    void tearDown() throws Exception {

        mocks.close();
    }
}
