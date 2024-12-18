package com.example.backend.controllers;

import com.example.backend.dtos.FollowingDTO;
import com.example.backend.entities.User;
import com.example.backend.exceptions.UserAlreadyFollowedException;
import com.example.backend.exceptions.UserAlreadyUnfollowedException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FollowControllerTest {
    private AutoCloseable mocks;
    private MockMvc mockMvc;

    @InjectMocks
    private FollowController followController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        mocks = openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(followController).build();
    }

    @Test
    public void followUserNormalReturnsOk() throws Exception {
        long followId = 123L;
        String requestBody = "{\"followId\": 123}";

        Mockito.doNothing().when(userService).followUser(followId);

        mockMvc.perform(post("/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    public void followAlreadyFollowedPersonReturnsConflict() throws Exception {
        Mockito.doThrow(new UserAlreadyFollowedException())
                .when(userService).followUser(Mockito.anyLong());
        String requestBody = "{\"followId\": 123}";

        mockMvc.perform(post("/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict());
    }

    @Test
    public void followNonExistingPersonReturnsNotFound() throws Exception {
        Mockito.doThrow(new UserNotFoundException("User not found"))
                .when(userService).followUser(Mockito.anyLong());
        String requestBody = "{\"followId\": 123}";

        mockMvc.perform(post("/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    public void unfollowUserNormalReturnsOk() throws Exception {
        long unfollowId = 123L;
        String requestBody = "{\"followId\": 123}";

        Mockito.doNothing().when(userService).unfollowUser(unfollowId);

        mockMvc.perform(post("/unfollow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    public void unfollowAlreadyUnfollowedPersonReturnsConflict() throws Exception {
        Mockito.doThrow(new UserAlreadyUnfollowedException())
                .when(userService).unfollowUser(Mockito.anyLong());
        String requestBody = "{\"followId\": 123}";

        mockMvc.perform(post("/unfollow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict());
    }

    @Test
    public void unfollowNonExistingPersonReturnsNotFound() throws Exception {
        Mockito.doThrow(new UserNotFoundException("User not found"))
                .when(userService).unfollowUser(Mockito.anyLong());
        String requestBody = "{\"followId\": 123}";

        mockMvc.perform(post("/unfollow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getFollowingCountReturnsOk() throws Exception {
        Mockito.when(userService.getFollowingCount()).thenReturn(123L);
        mockMvc.perform(get("/following-count")).andExpect(status().isOk());
    }

    @Test
    public void getFollowersCountReturnsOk() throws Exception {
        Mockito.when(userService.getFollowersCount()).thenReturn(123L);
        mockMvc.perform(get("/follower-count")).andExpect(status().isOk());
    }

    @Test
    public void getFollowersReturnsOk() throws Exception {
        List<FollowingDTO> mockFollowers = List.of(new FollowingDTO(3L,"rafy"), new FollowingDTO(4L,"armia"));
        Mockito.when(userService.getFollowers()).thenReturn(mockFollowers);

        mockMvc.perform(get("/followers")).andExpect(status().isOk());
    }

    @Test
    public void getFollowingReturnsOk() throws Exception {
        List<FollowingDTO> mockFollowing = List.of(new FollowingDTO(1L,"rafy"), new FollowingDTO(2L,"armia"));
        Mockito.when(userService.getFollowing()).thenReturn(mockFollowing);

        mockMvc.perform(get("/following"))
                .andExpect(status().isOk());
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }
}
