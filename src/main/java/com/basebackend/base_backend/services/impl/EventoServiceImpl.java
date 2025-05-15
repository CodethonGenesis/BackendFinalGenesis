package com.basebackend.base_backend.services.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basebackend.base_backend.entities.DTO.EventoDTO;
import com.basebackend.base_backend.entities.Evento;
import com.basebackend.base_backend.entities.Feed;
import com.basebackend.base_backend.entities.Usuario;
import com.basebackend.base_backend.repositories.EventoRepository;
import com.basebackend.base_backend.repositories.FeedRepository;
import com.basebackend.base_backend.services.EventoService;

@Service("EventoService")
public class EventoServiceImpl implements EventoService {

    private final EventoRepository repository;
    private final FeedRepository feedRepository;

    public EventoServiceImpl(EventoRepository repository, FeedRepository feedRepository) {
        this.repository = repository;
        this.feedRepository = feedRepository;
    }

    @Transactional(readOnly = true)
    public List<EventoDTO> findAllAsDTO() {
        List<EventoDTO> eventos = repository.findAllAsDTO();
        eventos.forEach(this::setSeguidoresToEventoDTO);
        eventos.forEach(this::setUsuariosToEventoDTO);
        return eventos;
    }

    @Transactional(readOnly = true)
    public Page<EventoDTO> findAllAsDTO(Pageable pageable) {
        Page<EventoDTO> page = repository.findAllAsDTO(pageable);
        page.forEach(eventoDTO -> {
            setSeguidoresToEventoDTO(eventoDTO);
            setUsuariosToEventoDTO(eventoDTO);
        });
        return page;
    }

    public List<EventoDTO> findEventosByUsuarioId(Long userId) {
        return repository.findEventosDTOByUsuarioId(userId);
    }

    public List<EventoDTO> findEventosByCategoriaId(Long categoriaId) {
        return repository.findEventosDTOByCategoriaId(categoriaId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Evento> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventoDTO> findDTOById(Long id) {
        Optional<EventoDTO> eventoDTO = repository.findDTOById(id);
        eventoDTO.ifPresent(dto -> {
            setSeguidoresToEventoDTO(dto);
            setUsuariosToEventoDTO(dto);
        });
        return eventoDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventoDTO> findDTOByName(String name) {
        Optional<EventoDTO> eventoDTO = repository.findDTOByName(name);
        eventoDTO.ifPresent(dto -> {
            setSeguidoresToEventoDTO(dto);
            setUsuariosToEventoDTO(dto);
        });
        return eventoDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> findDTOByEstado(Long id) {
        List<EventoDTO> eventoDTO = repository.findDTOByEstado(id);
        eventoDTO.forEach(dto -> {
            setSeguidoresToEventoDTO(dto);
            setUsuariosToEventoDTO(dto);
        });
        return eventoDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public Evento createEvento(Evento evento) {
        Evento savedEvento = repository.save(evento);
        savedEvento.setfCreacion(Instant.now());
        feedRepository.save(new Feed(savedEvento));
        return savedEvento;
    }

    @Override
    public Optional<Evento> update(Evento evento, Long id) {
        Optional<Evento> eventoOptional = repository.findById(id);

        if (eventoOptional.isPresent()) {
            Evento eventoDb = eventoOptional.get();
            eventoDb.setName(evento.getName());
            eventoDb.setDescripcion(evento.getDescripcion());
            eventoDb.setfInicio(evento.getfInicio());
            eventoDb.setfFin(evento.getfFin());
            eventoDb.setfTopeInscripcion(evento.getfTopeInscripcion());
            eventoDb.setCategoria(evento.getCategoria());
            eventoDb.setEstado(evento.getEstado());
            eventoDb.setLimiteUsuarios(evento.getLimiteUsuarios());
            eventoDb.setUbicacion(evento.getUbicacion());
            eventoDb.setLinkExterno(evento.getLinkExterno());
            eventoDb.setCoste(evento.getCoste());
            eventoDb.setCodigoVestimenta(String.valueOf(evento.getCodigoVestimenta()));
            return Optional.of(repository.save(eventoDb));
        }

        return Optional.empty();
    }

    public void setSeguidoresToEventoDTO(EventoDTO eventoDTO) {
        Optional<Evento> seguidores = repository.findById(eventoDTO.getId());
        if (seguidores.isPresent()) {
            List<Usuario> users = seguidores.get().getSeguidores();
            List<Long> userIds = users.stream().map(Usuario::getId).toList();
            eventoDTO.setSeguidores(userIds);
        }
    }

    public void setUsuariosToEventoDTO(EventoDTO eventoDTO) {
        Optional<Evento> evento = repository.findById(eventoDTO.getId());
        if (evento.isPresent()) {
            List<Usuario> users = evento.get().getUsuarios();
            List<Long> userIds = users.stream().map(Usuario::getId).toList();
            eventoDTO.setUsuarios(userIds);
        }
    }

    public List<EventoDTO> findEventosApuntadosByUserId(Long userId) {
        return repository.findDTOByUsuarioApuntado(userId);
    }

    @Override
    public Evento save(Evento evento) {
        return repository.save(evento);
    }

// Métodos específicos para gestionar la relación entre Usuario y Evento de forma segura
    @Transactional
    public void apuntarUsuarioAEvento(Evento evento, Usuario usuario) {
        // Utilizamos JPQL para evitar problemas con la persistencia de la contraseña
        repository.addUsuarioAEvento(evento.getId(), usuario.getId());
    }

    @Transactional
    public void desapuntarUsuarioDeEvento(Evento evento, Usuario usuario) {
        // Utilizamos JPQL para evitar problemas con la persistencia de la contraseña
        repository.removeUsuarioDeEvento(evento.getId(), usuario.getId());
    }
}
