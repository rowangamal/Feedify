package com.example.backend.postInteractions;

import com.example.backend.entities.Post;
import com.example.backend.entities.PostType;
import com.example.backend.exceptions.PostOutOfLimitException;
import com.example.backend.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreatePostCommand  extends PostCommand {

    public CreatePostCommand(Post post , PostRepository postRepository) {
        super(post , postRepository );

    }
    @Override
    public void execute() {
        if(super.post.getContent().length() > 1000)
            throw new PostOutOfLimitException("Post content is out of limit");
        postRepository.save(super.post);
    }
}
