package com.example.backend.services;


import com.example.backend.entities.PostType;
import com.example.backend.exceptions.TopicAlreadyExistsException;
import com.example.backend.exceptions.TopicNotFoundException;
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

    public List<PostType> getAllTopics() {
        return topicRepository.findAll();
    }

    public void addTopic(String postTopic) {
        if (postTopic == null) {
            throw new NullPointerException("Topic name cannot be null");
        }
        if (postTopic.isEmpty()) {
            throw new IllegalArgumentException("Topic name cannot be empty");
        }
        PostType postType = new PostType();
        postType.setName(postTopic);
        if (topicRepository.existsByName(postType.getName())) {
            throw new TopicAlreadyExistsException("Topic already exists");
        }
        topicRepository.save(postType);
    }

    public void deleteTopic(long postTypeId) {
        if (!topicRepository.existsById(postTypeId)) {
            throw new TopicNotFoundException("Topic does not exist");
        }
        String[] topics = {"Sport", "Technology", "Health", "Religion", "Troll", "Politics", "Personal"};
        for (String topic : topics) {
            if (topicRepository.findById(postTypeId).get().getName().equals(topic)) {
                throw new RuntimeException("Cannot delete default topic");
            }
        }
        topicRepository.deleteById(postTypeId);
    }

}
