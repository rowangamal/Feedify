package com.example.backend.services.search;

import com.example.backend.dtos.UserSearchDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchServiceTest {

    @Mock
    private SearchContext searchContext;

    @Mock
    private UsernameSearchStrategy usernameSearchStrategy;

    @Mock
    private EmailSearchStrategy emailSearchStrategy;

    @InjectMocks
    private SearchService searchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsersByUsername() {
        UserSearchDTO user1 = new UserSearchDTO(1L, "user1@gmail.com", "user1");
        UserSearchDTO user2 = new UserSearchDTO(2L, "user2@gmail.com", "user2");
        List<UserSearchDTO> expectedUsers = Arrays.asList(user1, user2);
        when(usernameSearchStrategy.search("user")).thenReturn(expectedUsers);
        when(searchContext.executeSearch("user")).thenReturn(expectedUsers);
        List<UserSearchDTO> result = searchService.getUsersByUsername("user");
        assertEquals(2, result.size());
        assertEquals("user1", result.getFirst().getUsername());
        assertEquals("user2", result.get(1).getUsername());
    }

    @Test
    void testGetUsersByUsername_NoResults() {
        List<UserSearchDTO> expectedUsers = List.of();
        when(usernameSearchStrategy.search("nonexistent")).thenReturn(expectedUsers);
        when(searchContext.executeSearch("nonexistent")).thenReturn(expectedUsers);
        List<UserSearchDTO> result = searchService.getUsersByUsername("nonexistent");
        assertEquals(0, result.size());
    }

    @Test
    void testGetUsersByEmail_NoResults() {
        List<UserSearchDTO> expectedUsers = List.of();
        when(emailSearchStrategy.search("nonexistent")).thenReturn(expectedUsers);
        when(searchContext.executeSearch("nonexistent")).thenReturn(expectedUsers);
        List<UserSearchDTO> result = searchService.getUsersByEmail("nonexistent");
        assertEquals(0, result.size());
    }

    @Test
    void testGetUsersByUsername_CaseInsensitive() {
        UserSearchDTO user1 = new UserSearchDTO(1L, "user1@gmail.com", "user1");
        List<UserSearchDTO> expectedUsers = List.of(user1);
        when(usernameSearchStrategy.search("USER1")).thenReturn(expectedUsers);
        when(searchContext.executeSearch("USER1")).thenReturn(expectedUsers);
        List<UserSearchDTO> result = searchService.getUsersByUsername("USER1");
        assertEquals(1, result.size());
        assertEquals("user1", result.getFirst().getUsername());
    }

    @Test
    void testGetUsersByUsername_EmptyQuery() {
        UserSearchDTO user1 = new UserSearchDTO(1L, "user1@gmail.com", "user1");
        UserSearchDTO user2 = new UserSearchDTO(2L, "user2@gmail.com", "user2");
        List<UserSearchDTO> expectedUsers = Arrays.asList(user1, user2);
        when(usernameSearchStrategy.search("")).thenReturn(expectedUsers);
        when(searchContext.executeSearch("")).thenReturn(expectedUsers);
        List<UserSearchDTO> result = searchService.getUsersByUsername("");
        assertEquals(2, result.size());
        assertEquals("user1", result.getFirst().getUsername());
        assertEquals("user2", result.get(1).getUsername());
    }

    @Test
    void testGetUsersByEmail_EmptyQuery() {
        UserSearchDTO user1 = new UserSearchDTO(1L, "user1@gmail.com", "user1");
        UserSearchDTO user2 = new UserSearchDTO(2L, "user2@gmail.com", "user2");
        List<UserSearchDTO> expectedUsers = Arrays.asList(user1, user2);
        when(emailSearchStrategy.search("")).thenReturn(expectedUsers);
        when(searchContext.executeSearch("")).thenReturn(expectedUsers);
        List<UserSearchDTO> result = searchService.getUsersByEmail("");
        assertEquals(2, result.size());
        assertEquals("user1@gmail.com", result.getFirst().getEmail());
        assertEquals("user2@gmail.com", result.get(1).getEmail());
    }

    @Test
    void testGetUsersByUsername_SpecialCharacters() {
        UserSearchDTO user1 = new UserSearchDTO(1L, "user1@gmail.com", "user1");
        List<UserSearchDTO> expectedUsers = List.of(user1);
        when(usernameSearchStrategy.search("user!")).thenReturn(expectedUsers);
        when(searchContext.executeSearch("user!")).thenReturn(expectedUsers);
        List<UserSearchDTO> result = searchService.getUsersByUsername("user!");
        assertEquals(1, result.size());
        assertEquals("user1", result.getFirst().getUsername());
    }

    @Test
    void testGetUsersByUsername_EmptyUsername() {
        UserSearchDTO user1 = new UserSearchDTO(1L, "user1@gmail.com", "");
        List<UserSearchDTO> expectedUsers = List.of(user1);
        when(usernameSearchStrategy.search("")).thenReturn(expectedUsers);
        when(searchContext.executeSearch("")).thenReturn(expectedUsers);
        List<UserSearchDTO> result = searchService.getUsersByUsername("");
        assertEquals(1, result.size());
        assertEquals("", result.getFirst().getUsername());
    }

}
