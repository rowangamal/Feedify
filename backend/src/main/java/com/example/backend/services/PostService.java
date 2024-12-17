package com.example.backend.services;

import com.example.backend.dtos.PostDTO;
import com.example.backend.entities.Post;
import com.example.backend.entities.User;
import com.example.backend.fileHandling.HandleImage;
import com.example.backend.fileHandling.HandlePostJson;
import com.example.backend.postInteractions.CreatePostCommand;
import com.example.backend.postInteractions.InvokePostCommand;
import com.example.backend.repositories.PostRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PostService implements IService {

    @Autowired
    private PostRepository PostRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private HandlePostJson handlePostJson ;

    private HandleImage handleImage = HandleImage.getInstance();
    public PostService() {
    }

    public void createPost(String postJson , MultipartFile image) throws Exception {
        String path = "../frontend/public/uploads/post/" ;
        String imageUrl = handleImage.saveImage(image , path);
        PostDTO post = handlePostJson.getPostDTO(postJson);
        Post convertPost = ConvertToPost(post , imageUrl);
        CreatePostCommand createPostCommand = new CreatePostCommand(convertPost , PostRepository);
        InvokePostCommand invokePostCommand = new InvokePostCommand(createPostCommand);
        invokePostCommand.execute();
    }

    public Post ConvertToPost(PostDTO postDTO , String imageURL) {
        Post post = new Post();
        post.setContent(postDTO.content);
        post.setPostTypes(postDTO.types);
        post.setImage(imageURL);
        post.setUser(new User(userService.getUserId()));
        return post;
    }
}
