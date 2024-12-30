

package com.example.backend.services.search;

import com.example.backend.dtos.UserSearchDTO;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Component
public class SearchContext {

    private ISearchStrategy searchStrategy;

    public List<UserSearchDTO> executeSearch(String query) {
        return searchStrategy.search(query);
    }
}