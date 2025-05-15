package com.basebackend.base_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import com.basebackend.base_backend.entities.Preferencias;
import com.basebackend.base_backend.entities.enums.Tema;

public interface PreferenciasService {

    List<Preferencias> findAll();

    Page<Preferencias> findAll(Pageable pageable);
    
    Optional<Preferencias> findById(@NonNull Long id);

    List<Preferencias> findByTema(@NonNull Tema tema);

    List<Preferencias> findByDaltonico(@NonNull Boolean daltonico);

    List<Preferencias> findByDiscapacidadVisual(@NonNull Boolean discapacidadVisual);

    Optional<Preferencias> update(Preferencias preferencias, Long id);
    
    void deleteById(Long id);

    Preferencias createPrefencias(Preferencias preferencias);
}
