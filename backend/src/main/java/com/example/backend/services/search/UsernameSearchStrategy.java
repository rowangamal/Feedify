package com.example.backend.services.search;

import com.example.backend.dtos.UserDTO;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsernameSearchStrategy implements ISearchStrategy {

    private final UserRepository userRepository;

    @Autowired
    public UsernameSearchStrategy(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> search(String query) {
        System.out.println(query);
        return userRepository.findAll().stream()
                .filter(user -> user.getUsername().toLowerCase().contains(query.toLowerCase()))
                .map(user -> new UserDTO(user.getId(), user.getEmail()))
                .collect(Collectors.toList());
    }
}
