package com.example.backend.postInteractions;


public class InvokePostCommand  {
    private final PostCommand postCommand;

    public InvokePostCommand(PostCommand postCommand ) {
        this.postCommand = postCommand;
    }


    public void execute() {
        postCommand.execute();
    }
}
