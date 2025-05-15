package com.basebackend.base_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.basebackend.base_backend.entities.Estado;

import jakarta.validation.constraints.NotNull;

public interface EstadoService {

    List<Estado> findAll();

    Page<Estado> findAll(Pageable pageable);

    Optional<Estado> findById(@NotNull Long id);

    Estado createEstado(Estado estado);

    Optional<Estado> update(Estado estado, Long id);

    void deleteById(Long id);
}
