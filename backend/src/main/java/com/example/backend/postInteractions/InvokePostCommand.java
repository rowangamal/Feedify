package com.example.backend.postInteractions;

import com.example.backend.entities.Post;

public class InvokePostCommand  {
    private PostCommand postCommand;

    public InvokePostCommand(PostCommand postCommand ) {
        this.postCommand = postCommand;
    }


    public void execute() {
        postCommand.execute();
    }
}
