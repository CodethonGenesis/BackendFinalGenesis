package com.basebackend.base_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.basebackend.base_backend.entities.DTO.EventoDTO;
import com.basebackend.base_backend.entities.Evento;


@Service
public interface EventoService {
    
    List<EventoDTO> findAllAsDTO();
    
    Page<EventoDTO> findAllAsDTO(Pageable pageable);

    Optional<Evento> findById(@NonNull Long id);

    Optional<EventoDTO> findDTOById(@NonNull Long id);

    Optional<EventoDTO> findDTOByName(@NonNull String name);

    List<EventoDTO> findDTOByEstado(@NonNull Long id);

    Optional<Evento> update(Evento evento, Long id);

    void deleteById(Long id);

    Evento createEvento(Evento evento);

    Evento save(Evento evento);
}
