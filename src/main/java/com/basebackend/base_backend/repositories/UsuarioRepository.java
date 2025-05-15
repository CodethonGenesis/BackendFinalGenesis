package com.basebackend.base_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.basebackend.base_backend.entities.DTO.UsuarioDTO;
import com.basebackend.base_backend.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.UsuarioDTO("
            + "u.id as id, u.userName as userName, u.registro as registro, u.email as email, u.name as name, u.lastName as lastName, "
            + "u.tipoDocumento as tipoDocumento, u.documento as documento, "
            + "p.id as preferenciasId, p.daltonico as daltonico, p.tema as tema, p.textSize as textSize, p.idioma as idioma, p.discapacidadVisual as discapacidadVisual) "
            + "FROM Usuario u "
            + "LEFT JOIN u.selfPreferencias p")
    List<UsuarioDTO> findAllAsDTO();

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.UsuarioDTO("
            + "u.id, u.userName, u.registro, u.email, u.name, u.lastName, "
            + "u.tipoDocumento, u.documento, "
            + "p.id, p.daltonico, p.tema, p.textSize, p.idioma, p.discapacidadVisual)"
            + "FROM Usuario u "
            + "LEFT JOIN u.selfPreferencias p")
    @NonNull
    Page<UsuarioDTO> findAllAsDTO(@NonNull Pageable pageable);

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.UsuarioDTO("
            + "u.id, u.userName, u.registro, u.email, u.name, u.lastName, "
            + "u.tipoDocumento, u.documento, "
            + "p.id, p.daltonico, p.tema, p.textSize, p.idioma, p.discapacidadVisual) "
            + "FROM Usuario u "
            + "LEFT JOIN u.selfPreferencias p "
            + "WHERE u.id = ?1")
    Optional<UsuarioDTO> findDTOById(Long id);

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.UsuarioDTO("
            + "u.id, u.userName, u.registro, u.email, u.name, u.lastName, "
            + "u.tipoDocumento, u.documento, "
            + "p.id, p.daltonico, p.tema, p.textSize, p.idioma, p.discapacidadVisual)"
            + "FROM Usuario u "
            + "LEFT JOIN u.selfPreferencias p "
            + "WHERE u.userName LIKE %?1%")
    Optional<UsuarioDTO> findDTOByUserNameContaining(String userName);

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.UsuarioDTO("
            + "u.id, u.userName, u.registro, u.email, u.name, u.lastName, "
            + "u.tipoDocumento, u.documento, "
            + "p.id, p.daltonico, p.tema, p.textSize, p.idioma, p.discapacidadVisual) "
            + "FROM Usuario u "
            + "LEFT JOIN u.selfPreferencias p "
            + "WHERE u.userName = ?1")
    Optional<UsuarioDTO> findDTOByUserName(String userName);

    @Override
    Optional<Usuario> findById(@NonNull Long id);

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.UsuarioDTO("
            + "u.id, u.userName, u.registro, u.email, u.name, u.lastName, "
            + "u.tipoDocumento, u.documento, "
            + "p.id, p.daltonico, p.tema, p.textSize, p.idioma, p.discapacidadVisual) "
            + "FROM Usuario u "
            + "LEFT JOIN u.selfPreferencias p "
            + "WHERE u.email LIKE %?1%")
    Optional<UsuarioDTO> findDTOByEmail(String email);

    Optional<Usuario> findByUserName(String userName);

    Optional<Usuario> findByEmail(String email);
}
