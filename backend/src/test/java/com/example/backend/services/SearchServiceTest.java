package com.example.backend.services;

import com.example.backend.dtos.InteractionsDTO;
import com.example.backend.services.search.EmailSearchStrategy;
import com.example.backend.services.search.SearchContext;
import com.example.backend.services.search.SearchService;
import com.example.backend.services.search.UsernameSearchStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
        InteractionsDTO user1 = new InteractionsDTO(1L, "user1@gmail.com", "user1");
        InteractionsDTO user2 = new InteractionsDTO(2L, "user2@gmail.com", "user2");
        List<InteractionsDTO> expectedUsers = Arrays.asList(user1, user2);
        when(usernameSearchStrategy.search("user")).thenReturn(expectedUsers);
        when(searchContext.executeSearch("user")).thenReturn(expectedUsers);
        List<InteractionsDTO> result = searchService.getUsersByUsername("user");
        assertEquals(2, result.size());
        assertEquals("user1", result.getFirst().getUsername());
        assertEquals("user2", result.get(1).getUsername());
    }

    @Test
    void testGetUsersByUsername_NoResults() {
        List<InteractionsDTO> expectedUsers = List.of();
        when(usernameSearchStrategy.search("nonexistent")).thenReturn(expectedUsers);
        when(searchContext.executeSearch("nonexistent")).thenReturn(expectedUsers);
        List<InteractionsDTO> result = searchService.getUsersByUsername("nonexistent");
        assertEquals(0, result.size());
    }

    @Test
    void testGetUsersByEmail_NoResults() {
        List<InteractionsDTO> expectedUsers = List.of();
        when(emailSearchStrategy.search("nonexistent")).thenReturn(expectedUsers);
        when(searchContext.executeSearch("nonexistent")).thenReturn(expectedUsers);
        List<InteractionsDTO> result = searchService.getUsersByEmail("nonexistent");
        assertEquals(0, result.size());
    }

    @Test
    void testGetUsersByUsername_CaseInsensitive() {
        InteractionsDTO user1 = new InteractionsDTO(1L, "user1@gmail.com", "user1");
        List<InteractionsDTO> expectedUsers = List.of(user1);
        when(usernameSearchStrategy.search("USER1")).thenReturn(expectedUsers);
        when(searchContext.executeSearch("USER1")).thenReturn(expectedUsers);
        List<InteractionsDTO> result = searchService.getUsersByUsername("USER1");
        assertEquals(1, result.size());
        assertEquals("user1", result.getFirst().getUsername());
    }

    @Test
    void testGetUsersByUsername_EmptyQuery() {
        InteractionsDTO user1 = new InteractionsDTO(1L, "user1@gmail.com", "user1");
        InteractionsDTO user2 = new InteractionsDTO(2L, "user2@gmail.com", "user2");
        List<InteractionsDTO> expectedUsers = Arrays.asList(user1, user2);
        when(usernameSearchStrategy.search("")).thenReturn(expectedUsers);
        when(searchContext.executeSearch("")).thenReturn(expectedUsers);
        List<InteractionsDTO> result = searchService.getUsersByUsername("");
        assertEquals(2, result.size());
        assertEquals("user1", result.getFirst().getUsername());
        assertEquals("user2", result.get(1).getUsername());
    }

    @Test
    void testGetUsersByEmail_EmptyQuery() {
        InteractionsDTO user1 = new InteractionsDTO(1L, "user1@gmail.com", "user1");
        InteractionsDTO user2 = new InteractionsDTO(2L, "user2@gmail.com", "user2");
        List<InteractionsDTO> expectedUsers = Arrays.asList(user1, user2);
        when(emailSearchStrategy.search("")).thenReturn(expectedUsers);
        when(searchContext.executeSearch("")).thenReturn(expectedUsers);
        List<InteractionsDTO> result = searchService.getUsersByEmail("");
        assertEquals(2, result.size());
        assertEquals("user1@gmail.com", result.getFirst().getEmail());
        assertEquals("user2@gmail.com", result.get(1).getEmail());
    }

    @Test
    void testGetUsersByUsername_SpecialCharacters() {
        InteractionsDTO user1 = new InteractionsDTO(1L, "user1@gmail.com", "user1");
        List<InteractionsDTO> expectedUsers = List.of(user1);
        when(usernameSearchStrategy.search("user!")).thenReturn(expectedUsers);
        when(searchContext.executeSearch("user!")).thenReturn(expectedUsers);
        List<InteractionsDTO> result = searchService.getUsersByUsername("user!");
        assertEquals(1, result.size());
        assertEquals("user1", result.getFirst().getUsername());
    }

    @Test
    void testGetUsersByUsername_EmptyUsername() {
        InteractionsDTO user1 = new InteractionsDTO(1L, "user1@gmail.com", "");
        List<InteractionsDTO> expectedUsers = List.of(user1);
        when(usernameSearchStrategy.search("")).thenReturn(expectedUsers);
        when(searchContext.executeSearch("")).thenReturn(expectedUsers);
        List<InteractionsDTO> result = searchService.getUsersByUsername("");
        assertEquals(1, result.size());
        assertEquals("", result.getFirst().getUsername());
    }

}
