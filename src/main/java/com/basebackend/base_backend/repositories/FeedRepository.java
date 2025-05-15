package com.basebackend.base_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.basebackend.base_backend.entities.DTO.FeedDTO;
import com.basebackend.base_backend.entities.Feed;

import jakarta.validation.constraints.NotNull;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.FeedDTO(f.id, f.evento.id, f.evento.name) FROM Feed f")
    List<FeedDTO> findAllAsDTO();

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.FeedDTO(f.id, f.evento.id, f.evento.name) FROM Feed f")
    @NotNull
    Page<FeedDTO> findAllAsDTO(@NotNull Pageable pageable);
    
    @Query("SELECT new com.basebackend.base_backend.entities.DTO.FeedDTO(f.id, f.evento.id, f.evento.name) FROM Feed f WHERE f.id = :id")
    Optional<FeedDTO> findDTOById(Long id);

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.FeedDTO(f.id, f.evento.id, f.evento.name) FROM Feed f WHERE f.evento.id = :eventoId")
    Optional<FeedDTO> findDTOByEvento(Long eventoId);
}
