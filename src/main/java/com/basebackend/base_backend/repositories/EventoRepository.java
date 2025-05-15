package com.basebackend.base_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.basebackend.base_backend.entities.DTO.EventoDTO;
import com.basebackend.base_backend.entities.Evento;

import jakarta.validation.constraints.NotNull;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.EventoDTO(e.id, e.name, e.descripcion, s.id, s.name, e.fCreacion, e.fInicio, e.fFin, e.fTopeInscripcion, f.id, c.id, c.name, e.codigoVestimenta, e.coste, e.dislikes, e.likes, e.limiteUsuarios, e.linkExterno, e.ubicacion, u.id, u.userName) "
            + "FROM Evento e "
            + "LEFT JOIN e.categoria c "
            + "LEFT JOIN e.usuario u "
            + "LEFT JOIN e.estado s "
            + "LEFT JOIN e.feed f")
    Page<EventoDTO> findAllAsDTO(@NotNull Pageable pageable);

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.EventoDTO(e.id, e.name, e.descripcion, s.id, s.name, e.fCreacion, e.fInicio, e.fFin, e.fTopeInscripcion, f.id, c.id, c.name, e.codigoVestimenta, e.coste, e.dislikes, e.likes, e.limiteUsuarios, e.linkExterno, e.ubicacion, u.id, u.userName) "
            + "FROM Evento e "
            + "LEFT JOIN e.categoria c "
            + "LEFT JOIN e.usuario u "
            + "LEFT JOIN e.estado s "
            + "LEFT JOIN e.feed f")
    List<EventoDTO> findAllAsDTO();

    @Override
    Optional<Evento> findById(Long id);

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.EventoDTO(e.id, e.name, e.descripcion, s.id, s.name, e.fCreacion, e.fInicio, e.fFin, e.fTopeInscripcion, f.id, c.id, c.name, e.codigoVestimenta, e.coste, e.dislikes, e.likes, e.limiteUsuarios, e.linkExterno, e.ubicacion, u.id, u.userName) "
            + "FROM Evento e "
            + "LEFT JOIN e.categoria c "
            + "LEFT JOIN e.usuario u "
            + "LEFT JOIN e.estado s "
            + "LEFT JOIN e.feed f "
            + "WHERE e.id = :id")
    Optional<EventoDTO> findDTOById(Long id);

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.EventoDTO(e.id, e.name, e.descripcion, s.id, s.name, e.fCreacion, e.fInicio, e.fFin, e.fTopeInscripcion, f.id, c.id, c.name, e.codigoVestimenta, e.coste, e.dislikes, e.likes, e.limiteUsuarios, e.linkExterno, e.ubicacion, u.id, u.userName) "
            + "FROM Evento e "
            + "LEFT JOIN e.categoria c "
            + "LEFT JOIN e.usuario u "
            + "LEFT JOIN e.estado s "
            + "LEFT JOIN e.feed f "
            + "WHERE e.name = :name")
    Optional<EventoDTO> findDTOByName(String name);

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.EventoDTO(e.id, e.name, e.descripcion, s.id, s.name, e.fCreacion, e.fInicio, e.fFin, e.fTopeInscripcion, f.id, c.id, c.name, e.codigoVestimenta, e.coste, e.dislikes, e.likes, e.limiteUsuarios, e.linkExterno, e.ubicacion, u.id, u.userName) "
            + "FROM Evento e "
            + "LEFT JOIN e.categoria c "
            + "LEFT JOIN e.usuario u "
            + "LEFT JOIN e.estado s "
            + "LEFT JOIN e.feed f "
            + "WHERE s.id = :id")
    List<EventoDTO> findDTOByEstado(Long id);

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.EventoDTO(e.id, e.name, e.descripcion, s.id, s.name, e.fCreacion, e.fInicio, e.fFin, e.fTopeInscripcion, f.id, c.id, c.name, e.codigoVestimenta, e.coste, e.dislikes, e.likes, e.limiteUsuarios, e.linkExterno, e.ubicacion, u.id, u.userName) "
            + "FROM Evento e "
            + "LEFT JOIN e.categoria c "
            + "LEFT JOIN e.usuario u "
            + "LEFT JOIN e.estado s "
            + "LEFT JOIN e.feed f "
            + "WHERE u.id = :userId")
    List<EventoDTO> findEventosDTOByUsuarioId(Long userId);

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.EventoDTO(e.id, e.name, e.descripcion, s.id, s.name, e.fCreacion, e.fInicio, e.fFin, e.fTopeInscripcion, f.id, c.id, c.name, e.codigoVestimenta, e.coste, e.dislikes, e.likes, e.limiteUsuarios, e.linkExterno, e.ubicacion, u.id, u.userName) "
            + "FROM Evento e "
            + "LEFT JOIN e.categoria c "
            + "LEFT JOIN e.usuario u "
            + "LEFT JOIN e.estado s "
            + "LEFT JOIN e.feed f "
            + "WHERE c.id = :categoriaId")
    List<EventoDTO> findEventosDTOByCategoriaId(Long categoriaId);

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.EventoDTO(e.id, e.name, e.descripcion, s.id, s.name, e.fCreacion, e.fInicio, e.fFin, e.fTopeInscripcion, f.id, c.id, c.name, e.codigoVestimenta, e.coste, e.dislikes, e.likes, e.limiteUsuarios, e.linkExterno, e.ubicacion, cr.id, cr.userName) "
            + "FROM Usuario u "
            + "JOIN u.eventosApuntados e "
            + "LEFT JOIN e.categoria c "
            + "LEFT JOIN e.usuario cr "
            + "LEFT JOIN e.estado s "
            + "LEFT JOIN e.feed f "
            + "WHERE u.id = :userId")
    List<EventoDTO> findDTOByUsuarioApuntado(@Param("userId") Long userId);

    @Modifying
    @Query(value = "INSERT INTO usuario_evento (usuario_id, evento_id) VALUES (:usuarioId, :eventoId) "
            + "ON CONFLICT (usuario_id, evento_id) DO NOTHING", nativeQuery = true)
    void addUsuarioAEvento(@Param("eventoId") Long eventoId, @Param("usuarioId") Long usuarioId);

    // Método personalizado para quitar un usuario de un evento sin cargar el objeto completo
    @Modifying
    @Query(value = "DELETE FROM usuario_evento WHERE usuario_id = :usuarioId AND evento_id = :eventoId",
            nativeQuery = true)
    void removeUsuarioDeEvento(@Param("eventoId") Long eventoId, @Param("usuarioId") Long usuarioId);

}
