package com.basebackend.base_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.basebackend.base_backend.entities.DTO.LogroUserDTO;
import com.basebackend.base_backend.entities.Logro;

public interface LogroService {

    List<LogroUserDTO> findAllAsDTO();

    Page<LogroUserDTO> findAllAsDTO(Pageable pageable);

    Optional<LogroUserDTO> findDTOById(Long id);

    Optional<Logro> findById(Long id);

    List<LogroUserDTO> findByDescripcionContainingIgnoreCase(String descripcion);

    List<LogroUserDTO> findByName(String name);
    
    List<LogroUserDTO> findByUsuariosId(Long usuarioId);

    Logro createLogro(Logro logro);

    Logro updateLogro(Long id, Logro logro);

    void deleteLogro(Long id);
}
