package com.example.backend.services.search;

import com.example.backend.dtos.UserSearchDTO;
import java.util.List;

public interface ISearchStrategy {
    List<UserSearchDTO> search(String query);
}
