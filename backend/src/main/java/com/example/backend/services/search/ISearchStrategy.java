package com.example.backend.services.search;

import com.example.backend.dtos.InteractionsDTO;

import java.util.List;

public interface ISearchStrategy {
    List<InteractionsDTO> search(String query);
}
