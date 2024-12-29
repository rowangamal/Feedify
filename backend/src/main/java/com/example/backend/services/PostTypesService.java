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

    public PostType getPostTypeById(long id) {
        return topicRepository.findById(id).orElseThrow(() -> new TopicNotFoundException("Topic not found"));
    }

    public List<PostType> getAllTopics() {
        return topicRepository.findAll();
    }

    public void addTopic(String postTopic) {
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
            if (topicRepository.existsByName(topic)) {
                if (topicRepository.findById(postTypeId).get().getName().equals(topic)) {
                    throw new RuntimeException("Cannot delete default topic");
                }
            }
        }
        topicRepository.deleteById(postTypeId);
    }

}
