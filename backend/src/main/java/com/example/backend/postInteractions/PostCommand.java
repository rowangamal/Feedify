package com.example.backend.postInteractions;

import com.example.backend.entities.Post;
import com.example.backend.repositories.PostRepository;


public abstract class PostCommand {
    protected Post post;
    protected final PostRepository postRepository;
    public PostCommand(Post post, PostRepository postRepository) {
        this.post = post;
        this.postRepository = postRepository;
    }

    public abstract void execute() ;
}
