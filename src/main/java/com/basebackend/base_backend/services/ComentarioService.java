package com.basebackend.base_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import com.basebackend.base_backend.entities.Comentario;
import com.basebackend.base_backend.entities.DTO.CommentDTO;

import jakarta.validation.constraints.NotNull;

public interface ComentarioService {

    List<CommentDTO> findAllAsDTO();

    @NotNull
    Page<CommentDTO> findAllAsDTO(@NotNull Pageable pageable);

    @NonNull
    Optional<Comentario> findById(@NonNull Long id);

    @NonNull
    Optional<CommentDTO> findDTOById(@NonNull Long id);

    List<CommentDTO> findDTOByContenidoContainingIgnoreCase(@NonNull String contenido);

    List<CommentDTO> findDTOByAutorId(@NonNull Long idAutor);

    List<CommentDTO> findAllDTOByOrderByLikesAsc();

    List<CommentDTO> findAllDTOByOrderByLikesDesc();

    List<CommentDTO> findAllDTOByOrderByDislikesDesc();

    List<CommentDTO> findAllDTOByOrderByDislikesAsc();

    Optional<Comentario> update(Comentario comentario, Long id);

    void deleteById(Long id);

    Comentario createComentario(Comentario comentario);
}
