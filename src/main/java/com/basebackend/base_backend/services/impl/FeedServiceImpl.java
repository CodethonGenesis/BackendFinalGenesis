package com.basebackend.base_backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basebackend.base_backend.entities.DTO.CommentDTO;
import com.basebackend.base_backend.entities.DTO.FeedDTO;
import com.basebackend.base_backend.entities.Feed;
import com.basebackend.base_backend.repositories.ComentarioRepository;
import com.basebackend.base_backend.repositories.FeedRepository;
import com.basebackend.base_backend.services.FeedService;

@Service("FeedService")
public class FeedServiceImpl implements FeedService {

    private final ComentarioRepository commentrepo;
    private final FeedRepository repository;

    public FeedServiceImpl(FeedRepository repository, ComentarioRepository commentrepo) {
        this.repository = repository;
        this.commentrepo = commentrepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedDTO> findAllAsDTO() {
        List<FeedDTO> feedDTOs = repository.findAllAsDTO();
        feedDTOs.forEach(this::setComentariosToFeedDTO);
        return feedDTOs;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedDTO> findAllAsDTO(Pageable pageable) {
        Page<FeedDTO> page = repository.findAllAsDTO(pageable);
        page.forEach(this::setComentariosToFeedDTO);
        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FeedDTO> findDTOById(Long id) {
        Optional<FeedDTO> dto = repository.findDTOById(id);
        dto.ifPresent(this::setComentariosToFeedDTO);
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FeedDTO> findDTOByEvento(Long idEvento) {
        Optional<FeedDTO> dto = repository.findDTOByEvento(idEvento);
        dto.ifPresent(this::setComentariosToFeedDTO);
        return dto;
    }

    @Override
    public Feed create(Feed feed) {
        return repository.save(feed);
    }

    @Override
    public Optional<Feed> update(Feed feed, Long id) {
        Optional<Feed> feedOptional = repository.findById(id);
        if (feedOptional.isPresent()) {
            Feed feedToUpdate = feedOptional.get();
            feedToUpdate.setEvento(feed.getEvento());
            feedToUpdate.setComentarios(feed.getComentarios());
            return Optional.of(repository.save(feedToUpdate));
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void setComentariosToFeedDTO(FeedDTO feedDTO) {
        List<CommentDTO> comentarios = commentrepo.findDTOByFeed(feedDTO.getId());
        feedDTO.setComentarios(comentarios == null || comentarios.isEmpty() ? null : comentarios);
    }
}
