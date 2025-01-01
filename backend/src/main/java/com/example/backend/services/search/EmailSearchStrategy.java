
package com.example.backend.services.search;

import com.example.backend.dtos.InteractionsDTO;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmailSearchStrategy implements ISearchStrategy {

    private final UserRepository userRepository;

    @Autowired
    public EmailSearchStrategy(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<InteractionsDTO> search(String query) {
        return userRepository.findAll().stream()
                .filter(user -> {
                    String localPart = user.getEmail().split("@")[0];
                    return localPart.toLowerCase().contains(query.toLowerCase());
                })
                .map(user -> new InteractionsDTO(user.getId(), user.getEmail(), user.getUsername()))
                .collect(Collectors.toList());
    }
}