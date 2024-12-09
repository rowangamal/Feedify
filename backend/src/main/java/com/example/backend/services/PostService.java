package com.example.backend.services;
import com.example.backend.dtos.PostDTO;
import com.example.backend.entities.Post;
import com.example.backend.entities.User;
import com.example.backend.postInteractions.CreatePostCommand;
import com.example.backend.postInteractions.InvokePostCommand;
import com.example.backend.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService implements IService {

    @Autowired
    private PostRepository PostRepository;

    @Autowired
    private UserService userService;
    public PostService() {
    }

    public void createPost(PostDTO post) {
        Post convertPost = ConvertToPost(post);
        CreatePostCommand createPostCommand = new CreatePostCommand(convertPost , PostRepository);
        InvokePostCommand invokePostCommand = new InvokePostCommand(createPostCommand);
        invokePostCommand.execute();
    }

    public Post ConvertToPost(PostDTO postDTO) {
        Post post = new Post();
        post.setContent(postDTO.content);
        post.setPostTypes(postDTO.types);
        post.setImage(postDTO.imageURL);
        post.setUser(new User(userService.getUserId()));
        return post;
    }
}
