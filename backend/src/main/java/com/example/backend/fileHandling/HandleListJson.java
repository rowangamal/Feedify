package com.example.backend.fileHandling;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class HandleListJson {
    @Autowired
    private Gson gson ;

    public HandleListJson() {
    }
    public List<Long> getInterests(String interestsJson) {
        Type listType = new TypeToken<List<Long>>() {}.getType();
        return gson.fromJson(interestsJson, listType);
    }
}
