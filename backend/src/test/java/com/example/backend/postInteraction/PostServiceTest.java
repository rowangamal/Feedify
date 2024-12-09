package com.example.backend.postInteraction;

import com.example.backend.dtos.PostDTO;
import com.example.backend.entities.Post;
import com.example.backend.repositories.PostRepository;
import com.example.backend.services.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.openMocks;

public class PostServiceTest {
    @Test
    void testMappingBetweenPostDTOAndPost() {
        PostService postService = new PostService();
        PostDTO postDTO = new PostDTO();
        postDTO.content = "I am Rafy";
        postDTO.imageURL = "rafy/hany/said";
        postDTO.types = new ArrayList<>();
        Post post = postService.ConvertToPost(postDTO);
        assertEquals(postDTO.content, post.getContent());
        assertEquals(postDTO.imageURL, post.getImage());
        assertEquals(postDTO.types, post.getPostTypes());
    }


}
