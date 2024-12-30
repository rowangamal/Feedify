package com.example.backend.services;

import com.example.backend.entities.PostType;
import com.example.backend.exceptions.TopicAlreadyExistsException;
import com.example.backend.exceptions.TopicNotFoundException;
import com.example.backend.repositories.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PostTypesServiceTest {
    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private PostTypesService postTypesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testGetAllTopics_NoTopics() {
        when(topicRepository.findAll()).thenReturn(new ArrayList<>());
        assertEquals(0, postTypesService.getAllTopics().size());
    }

    @Test
    public void testGetAllTopics_OneTopic() {
        PostType postType = new PostType(1l, "Test");
        when(topicRepository.findAll()).thenReturn(new ArrayList<>(List.of(postType)));
        assertEquals(1, postTypesService.getAllTopics().size());
        assertEquals(postType.getName(), postTypesService.getAllTopics().get(0).getName());
    }


    @Test
    public void TestAddTopic_Success(){
        String name = "Test";
        when(topicRepository.existsByName("Test")).thenReturn(false);
        postTypesService.addTopic("Test");
    }

    @Test
    public void TestAddTopic_ExistsByName(){
        String name = "Test";
        when(topicRepository.existsByName(name)).thenReturn(true);
        assertThrows(TopicAlreadyExistsException.class, () -> postTypesService.addTopic(name));
    }

    @Test
    public void TestAddTopic_NullName(){
        String name = null;
        assertThrows(NullPointerException.class, () -> postTypesService.addTopic(name));
    }

    @Test
    public void TestAddTopic_EmptyName(){
        String name = "";
        assertThrows(IllegalArgumentException.class, () -> postTypesService.addTopic(name));
    }

    @Test
    public void TestDeleteTopic_Success(){
        long id = 1;
        when(topicRepository.existsById(id)).thenReturn(true);
        when(topicRepository.findById(id)).thenReturn(java.util.Optional.of(new PostType(id, "Test")));
        postTypesService.deleteTopic(id);
    }

    @Test
    public void TestDeleteTopic_NonExistent(){
        when(topicRepository.existsById(1l)).thenReturn(false);
        assertThrows(TopicNotFoundException.class, () -> postTypesService.deleteTopic(1l));
    }

    @Test
    public void TestDeleteTopic_RefuseDefaultTopic(){
        long id = 1;
        when(topicRepository.existsById(id)).thenReturn(true);
        when(topicRepository.findById(id)).thenReturn(java.util.Optional.of(new PostType(id, "Sport")));
        assertThrows(RuntimeException.class, () -> postTypesService.deleteTopic(id));
    }

}