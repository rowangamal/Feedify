package com.example.backend.FileHandling;

import com.example.backend.dtos.PostDTO;
import com.example.backend.entities.PostType;
import com.example.backend.fileHandling.HandleListJson;
import com.example.backend.fileHandling.HandlePostJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;


public class JsonHandleTest {
    @InjectMocks
    private HandlePostJson handlePostJson;
    @InjectMocks
    private HandleListJson handleListJson;
    @Mock
    private Gson gson;
    private AutoCloseable mocks;
    @BeforeEach
    void setUp() {
        mocks = openMocks(this);
    }
    @Test
    void testMappingBetweenPostDTOAndPost() {

        String json = "{\"content\":\"I am Rafy\",\"topics\":[{\"name\":\"Sport\"}],\"imageURL\":\"\"}";
        PostDTO postDTO = new PostDTO();
        postDTO.content = "I am Rafy";
        postDTO.imageURL = "";
        postDTO.types = new ArrayList<>();
        PostType postType = new PostType();
        postType.setName("Sport");
        postDTO.types.add(postType);
        PostDTO postDTO1 = new PostDTO();
        postDTO1.content = "I am Rafy";
        postDTO1.imageURL = "";
        postDTO1.types = new ArrayList<>();
        PostType postType1 = new PostType();
        postType1.setName("Sport");
        postDTO1.types.add(postType1);

        when(gson.fromJson(json, PostDTO.class)).thenReturn(postDTO1);
        PostDTO postDTO2 = handlePostJson.getPostDTO(json);

        assertNotNull(postDTO2);
        assertEquals(postDTO.content, postDTO2.content);
        assertEquals(postDTO.imageURL, postDTO2.imageURL);
        assertEquals(postDTO.types.size(), postDTO2.types.size());
        verify(gson, times(1)).fromJson(json, PostDTO.class);
    }

    @Test
    void testMappingBetweenListAndList() {

        String json = "[1,2,3,4]";
        List<Long> expectedList = new ArrayList<>();
        expectedList.add(1L);
        expectedList.add(2L);
        expectedList.add(3L);
        expectedList.add(4L);

        Type listType = new TypeToken<List<Long>>() {}.getType();
        when(gson.fromJson(eq(json), eq(listType))).thenReturn(expectedList);
        List<Long> actualList = handleListJson.getInterests(json);

        assertNotNull(actualList);
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), actualList.get(i));
        }
        verify(gson, times(1)).fromJson(eq(json), eq(listType));
    }
    @AfterEach
    void releaseMocks() throws Exception {
        mocks.close();
    }


}
