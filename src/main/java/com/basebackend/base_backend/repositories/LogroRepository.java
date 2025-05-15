package com.basebackend.base_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.basebackend.base_backend.entities.DTO.LogroUserDTO;
import com.basebackend.base_backend.entities.Logro;

public interface LogroRepository extends JpaRepository<Logro, Long> {

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.LogroUserDTO(l.id, l.name, l.icono, l.descripcion, u.id, u.userName, u.email) FROM Logro l Join l.usuarios u")
    List<LogroUserDTO> findAllAsDTO();

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.LogroUserDTO(l.id, l.name, l.icono, l.descripcion, u.id, u.userName, u.email) FROM Logro l Join l.usuarios u")
    Page<LogroUserDTO> findAllAsDTO(Pageable pageable);

    Optional<LogroUserDTO> findDTOById(Long id);

    @Override
    Optional<Logro> findById(Long id);

    List<LogroUserDTO> findByDescripcionContainingIgnoreCase(String descripcion);

    List<LogroUserDTO> findByName(String name);

    List<LogroUserDTO> findByUsuariosId(Long usuarioId);
}
