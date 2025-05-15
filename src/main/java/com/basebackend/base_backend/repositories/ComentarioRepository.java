package com.basebackend.base_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.basebackend.base_backend.entities.Comentario;
import com.basebackend.base_backend.entities.DTO.CommentDTO;

import jakarta.validation.constraints.NotNull;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.CommentDTO("
            + "c.id, c.contenido, c.fecha, c.likes, c.dislikes, "
            + "f.id, u.id, u.userName, u.email) "
            + "FROM Comentario c JOIN c.autor u JOIN c.feed f")
    List<CommentDTO> findAllAsDTO();

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.CommentDTO("
            + "c.id, c.contenido, c.fecha, c.likes, c.dislikes, "
            + "f.id, u.id, u.userName, u.email) "
            + "FROM Comentario c JOIN c.autor u JOIN c.feed f")
    @NotNull
    Page<CommentDTO> findAllAsDTO(@NotNull Pageable pageable);

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.CommentDTO("
            + "c.id, c.contenido, c.fecha, c.likes, c.dislikes, "
            + "f.id, u.id, u.userName, u.email) "
            + "FROM Comentario c JOIN c.autor u JOIN c.feed f "
            + "WHERE c.id = :id")
    Optional<CommentDTO> findDTOById(Long id);

    @Override
    Optional<Comentario> findById(Long id);

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.CommentDTO("
            + "c.id, c.contenido, c.fecha, c.likes, c.dislikes, "
            + "f.id, u.id, u.userName, u.email) "
            + "FROM Comentario c JOIN c.autor u JOIN c.feed f "
            + "WHERE LOWER(c.contenido) LIKE LOWER(CONCAT('%', :contenido, '%'))")
    List<CommentDTO> findDTOByContenidoContainingIgnoreCase(String contenido);

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.CommentDTO("
            + "c.id, c.contenido, c.fecha, c.likes, c.dislikes, "
            + "f.id, u.id, u.userName, u.email) "
            + "FROM Comentario c JOIN c.autor u JOIN c.feed f "
            + "WHERE u.id = :idAutor")
    List<CommentDTO> findDTOByAutorId(Long idAutor);

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.CommentDTO("
            + "c.id, c.contenido, c.fecha, c.likes, c.dislikes, "
            + "f.id, u.id, u.userName, u.email) "
            + "FROM Comentario c JOIN c.autor u JOIN c.feed f "
            + "ORDER BY c.likes ASC")
    List<CommentDTO> findAllDTOByOrderByLikesAsc();

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.CommentDTO("
            + "c.id, c.contenido, c.fecha, c.likes, c.dislikes, "
            + "f.id, u.id, u.userName, u.email) "
            + "FROM Comentario c JOIN c.autor u JOIN c.feed f "
            + "ORDER BY c.likes DESC")
    List<CommentDTO> findAllDTOByOrderByLikesDesc();

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.CommentDTO("
            + "c.id, c.contenido, c.fecha, c.likes, c.dislikes, "
            + "f.id, u.id, u.userName, u.email) "
            + "FROM Comentario c JOIN c.autor u JOIN c.feed f "
            + "ORDER BY c.dislikes DESC")
    List<CommentDTO> findAllDTOByOrderByDislikesDesc();

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.CommentDTO("
            + "c.id, c.contenido, c.fecha, c.likes, c.dislikes, "
            + "f.id, u.id, u.userName, u.email) "
            + "FROM Comentario c JOIN c.autor u JOIN c.feed f "
            + "ORDER BY c.dislikes ASC")
    List<CommentDTO> findAllDTOByOrderByDislikesAsc();

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.CommentDTO("
            + "c.id, c.contenido, c.fecha, c.likes, c.dislikes, "
            + "f.id, u.id, u.userName, u.email) "
            + "FROM Comentario c JOIN c.autor u JOIN c.feed f "
            + "WHERE f.id = :idFeed")
    List<CommentDTO> findDTOByFeed(Long idFeed);
}
