package com.basebackend.base_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basebackend.base_backend.entities.Preferencias;
import com.basebackend.base_backend.entities.enums.Tema;

import jakarta.validation.constraints.NotNull;

@Repository
public interface PreferenciasRepository extends JpaRepository<Preferencias, Long>{

    @Override
    List<Preferencias> findAll();

    @Override
    @NotNull
    Page<Preferencias> findAll(@NotNull Pageable pageable);

    @Override
    Optional<Preferencias> findById(Long id);

    List<Preferencias> findByTema(Tema tema);

    List<Preferencias> findByDaltonico(Boolean daltonico);

    List<Preferencias> findByDiscapacidadVisual(Boolean discapacidadVisual);
}
