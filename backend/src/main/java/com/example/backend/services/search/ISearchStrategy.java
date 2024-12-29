package com.example.backend.services.search;

import com.example.backend.dtos.UserDTO;
import java.util.List;

public interface ISearchStrategy {
    List<UserDTO> search(String query);
}
