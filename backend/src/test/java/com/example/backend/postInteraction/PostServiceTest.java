package com.example.backend.postInteraction;

import com.example.backend.dtos.PostDTO;
import com.example.backend.entities.Post;
import com.example.backend.entities.PostType;
import com.example.backend.fileHandling.HandlePostJson;
import com.example.backend.repositories.PostRepository;
import com.example.backend.services.PostService;
import com.example.backend.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class PostServiceTest {
    @InjectMocks
    private PostService postService;
    @Mock
    private UserService userService;
    @Mock
    private HandlePostJson handlePostJson;

    @Mock
    private PostRepository postRepository;
    private AutoCloseable mocks;
    @BeforeEach
    void setUp() {
        mocks = openMocks(this);
    }
    @Test
    void testMappingBetweenPostDTOAndPost() {

        when(userService.getUserId()).thenReturn(1L);
        PostDTO postDTO = new PostDTO();
        postDTO.content = "I am Rafy";
        postDTO.imageURL = "rafy/hany/said";
        postDTO.types = new ArrayList<>();
        Post post = postService.ConvertToPost(postDTO, postDTO.imageURL);

        assertEquals(postDTO.content, post.getContent());
        assertEquals(postDTO.imageURL, post.getImage());
        assertEquals(postDTO.types, post.getPostTypes());
        assertEquals(1L, post.getUser().getId());
        verify(userService , times(1)).getUserId();
    }
    @Test
    void createPostTest() throws Exception {
        PostDTO postDTO = new PostDTO();
        postDTO.content = "I am Rafy";
        postDTO.imageURL = "rafy/hany/said";
        postDTO.types = new ArrayList<>();
        PostType postType = new PostType();
        postType.setName("Sport");
        postDTO.types.add(postType);
        String json = "{\"content\":\"I am Rafy\",\"topics\":[{\"name\":\"Sport\"}],\"imageURL\":\"\"}";

        when(userService.getUserId()).thenReturn(1L);
        when(handlePostJson.getPostDTO(json)).thenReturn(postDTO);
        when(postRepository.save(any(Post.class))).thenReturn(new Post());

        postService.createPost(json, null);
        verify(postRepository, times(1)).save(any(Post.class));
    }
    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

}
