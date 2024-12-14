package com.example.backend.autoLoad;

import com.example.backend.entities.PostType;
import com.example.backend.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PostTypeLoader implements CommandLineRunner {

    @Autowired
    TopicRepository topicRepository ;
    @Override
    public void run(String... args) throws Exception {
        if(!topicRepository.existsByName("Sport"))
            topicRepository.save(new PostType("Sport"));
        if(!topicRepository.existsByName("Technology"))
            topicRepository.save(new PostType("Technology"));
        if(!topicRepository.existsByName("Health"))
            topicRepository.save(new PostType("Health"));
        if(!topicRepository.existsByName("Religion"))
            topicRepository.save(new PostType("Religion"));
        if(!topicRepository.existsByName("Troll"))
            topicRepository.save(new PostType("Troll"));
        if(!topicRepository.existsByName("Politics"))
            topicRepository.save(new PostType("Politics"));
        if(!topicRepository.existsByName("Personal"))
            topicRepository.save(new PostType("Personal"));
    }
}
