package com.basebackend.base_backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basebackend.base_backend.entities.Comentario;
import com.basebackend.base_backend.entities.DTO.CommentDTO;
import com.basebackend.base_backend.repositories.ComentarioRepository;
import com.basebackend.base_backend.services.ComentarioService;

@Service("ComentarioService")
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository repository;

    public ComentarioServiceImpl(ComentarioRepository repository) {
        this.repository = repository;
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<CommentDTO> findAllAsDTO() {
        return repository.findAllAsDTO();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CommentDTO> findAllAsDTO(Pageable pageable) {
        return repository.findAllAsDTO(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentDTO> findDTOById(Long id) {
        return repository.findDTOById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comentario> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> findDTOByContenidoContainingIgnoreCase(String contenido) {
        return repository.findDTOByContenidoContainingIgnoreCase(contenido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> findDTOByAutorId(Long idAutor) {
        return repository.findDTOByAutorId(idAutor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> findAllDTOByOrderByLikesAsc() {
        return repository.findAllDTOByOrderByLikesAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> findAllDTOByOrderByLikesDesc() {
        return repository.findAllDTOByOrderByLikesDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> findAllDTOByOrderByDislikesDesc() {
        return repository.findAllDTOByOrderByDislikesDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> findAllDTOByOrderByDislikesAsc() {
        return repository.findAllDTOByOrderByDislikesAsc();
    }

    @Override
    public Optional<Comentario> update(Comentario comentario, Long id) {
        Optional<Comentario> comentarioOptional = repository.findById(id);
        
        if(comentarioOptional.isPresent()){
            Comentario comDb = comentarioOptional.get();
            comDb.setContenido(comentario.getContenido());
            comDb.setLikes(comentario.getLikes());
            comDb.setDislikes(comentario.getDislikes());
            comDb.setAutor(comentario.getAutor());
            comDb.setFecha(comentario.getFecha());

            return Optional.of(repository.save(comDb));
        }

        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Comentario createComentario(Comentario comentario) {
        return repository.save(comentario);
    }
}
