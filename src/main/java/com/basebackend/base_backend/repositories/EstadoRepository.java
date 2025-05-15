package com.basebackend.base_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basebackend.base_backend.entities.Estado;

import jakarta.validation.constraints.NotNull;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
    @Override
    List<Estado> findAll();

    @Override
    @NotNull
    Page<Estado> findAll(@NotNull Pageable pageable);

    @Override
    Optional<Estado> findById(Long id);


}
