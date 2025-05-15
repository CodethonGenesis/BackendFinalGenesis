package com.basebackend.base_backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basebackend.base_backend.entities.Estado;
import com.basebackend.base_backend.repositories.EstadoRepository;
import com.basebackend.base_backend.services.EstadoService;

@Service("EstadoService")
public class EstadoServiceImpl implements EstadoService {

    private final EstadoRepository repository;

    public EstadoServiceImpl(EstadoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estado> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Estado> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Estado> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Estado createEstado(Estado estado) {
        return repository.save(estado);
    }

    @Override
    public Optional<Estado> update(Estado estado, Long id) {
        Optional<Estado> estadoOptional = repository.findById(id);
        if (estadoOptional.isPresent()) {
            Estado estadoToUpdate = estadoOptional.get();
            estadoToUpdate.setName(estado.getName());
            estadoToUpdate.setEventos(estado.getEventos());
            return Optional.of(repository.save(estado));
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
