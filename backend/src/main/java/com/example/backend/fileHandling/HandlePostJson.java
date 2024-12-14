package com.example.backend.fileHandling;

import com.example.backend.dtos.PostDTO;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class HandlePostJson {
    private final Gson gson = new Gson();

    public HandlePostJson() {
    }
    public PostDTO getPostDTO(String postJson) {
        return gson.fromJson(postJson, PostDTO.class);
    }
}
