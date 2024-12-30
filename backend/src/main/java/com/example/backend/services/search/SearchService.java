package com.example.backend.services.search;

import com.example.backend.dtos.UserSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private SearchContext searchContext;

    @Autowired
    private UsernameSearchStrategy usernameSearchStrategy;

    @Autowired
    private EmailSearchStrategy emailSearchStrategy;

    public List<UserSearchDTO> getUsersByUsername(String username) {
        searchContext.setSearchStrategy(usernameSearchStrategy);
        return searchContext.executeSearch(username);
    }

    public List<UserSearchDTO> getUsersByEmail(String email) {
        searchContext.setSearchStrategy(emailSearchStrategy);
        return searchContext.executeSearch(email);
    }
}
