package com.basebackend.base_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.basebackend.base_backend.entities.DTO.FeedDTO;
import com.basebackend.base_backend.entities.Feed;

import jakarta.validation.constraints.NotNull;

public interface FeedService {

    List<FeedDTO> findAllAsDTO();

    Page<FeedDTO> findAllAsDTO(Pageable pageable);

    Optional<FeedDTO> findDTOById(@NotNull Long id);

    Optional<FeedDTO> findDTOByEvento(@NotNull Long idEvento);
    
    Feed create(Feed feed);

    Optional<Feed> update(Feed feed, Long id);

    void deleteById(Long id);

}
