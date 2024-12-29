package com.example.backend.services;


import com.example.backend.entities.PostType;
import com.example.backend.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTypesService {
    @Autowired
    private TopicRepository topicRepository;
    public List<PostType> getPostTypes() {
        return topicRepository.findAll();
    }
}
