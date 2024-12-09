package com.example.backend.autoLoad;

import com.example.backend.entities.PostType;
import com.example.backend.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PostTypeLoader implements CommandLineRunner {

    @Autowired
    TopicRepository topicRepository ;
    @Override
    public void run(String... args) throws Exception {
        if(topicRepository.count() == 0){
            topicRepository.saveAll(
                    List.of(
                            new PostType("Sport"),
                            new PostType("Technology"),
                            new PostType("Health"),
                            new PostType("Religion"),
                            new PostType("Troll"),
                            new PostType("Politics")
                    )
            );
        }
    }
}
