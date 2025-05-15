package com.basebackend.base_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.basebackend.base_backend.entities.Categoria;
import com.basebackend.base_backend.entities.DTO.CategoryUserDTO;
import com.basebackend.base_backend.entities.DTO.EventoDTO;

import jakarta.validation.constraints.NotNull;

public interface CategoryService {

    List<Categoria> findAll();

    @NotNull
    Page<CategoryUserDTO> findAllAsDTO(@NotNull Pageable pageable);

    @NotNull
    Optional<CategoryUserDTO> findDTOById(@NotNull Long id);

    @NotNull
    Optional<Categoria> findById(@NotNull Long id);

    @NotNull
    List<CategoryUserDTO> findByName(@NotNull String name);
    
    @NotNull
    List<CategoryUserDTO> findByDescripcionContainingIgnoreCase(@NotNull String description);

    @NotNull
    List<CategoryUserDTO> findByNameContainigngIgnoreCase(@NotNull String name);

    @NotNull
    List<EventoDTO> getEventosByCategoria(@NotNull Long idCategoria);

    Categoria createCategory(Categoria categoria);

    Categoria updateCategory(Long id, Categoria categoria);

    void deleteCategory(Long id);
}
