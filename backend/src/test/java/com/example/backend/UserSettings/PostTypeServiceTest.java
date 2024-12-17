package com.example.backend.UserSettings;

import com.example.backend.entities.PostType;
import com.example.backend.repositories.TopicRepository;
import com.example.backend.services.PostTypesService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class PostTypeServiceTest {

    @InjectMocks
    private PostTypesService postTypesService;

    @Mock
    private TopicRepository topicRepository;

    private AutoCloseable mocks;
    @BeforeEach
    void setUp() {
        mocks = openMocks(this);
    }
    @Test
    void getPostTypesTest() {
        List<PostType> postTypes = new ArrayList<>();
        postTypes.add(new PostType(1, "postType1"));
        postTypes.add(new PostType(2, "postType2"));
        postTypes.add(new PostType(3, "postType3"));
        when(topicRepository.findAll()).thenReturn(postTypes);
        List<PostType> postTypesTest = postTypesService.getPostTypes();
        assert(postTypesTest.size() == 3);
        verify(topicRepository, times(1)).findAll();
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

}
