package com.example.backend.fileHandling;

import com.example.backend.dtos.PostDTO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HandlePostJson {

    @Autowired
    private  Gson gson ;

    public HandlePostJson() {
    }
    public PostDTO getPostDTO(String postJson) {
        return gson.fromJson(postJson, PostDTO.class);
    }


}
