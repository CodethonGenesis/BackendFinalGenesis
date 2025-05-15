package com.basebackend.base_backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.basebackend.base_backend.entities.DTO.LogroUserDTO;
import com.basebackend.base_backend.entities.Logro;
import com.basebackend.base_backend.repositories.LogroRepository;
import com.basebackend.base_backend.services.LogroService;

@Service
public class LogroServiceImpl implements LogroService {

    private final LogroRepository logroRepository;

    public LogroServiceImpl(LogroRepository logroRepository) {
        this.logroRepository = logroRepository;
    }

    @Override
    public List<LogroUserDTO> findAllAsDTO() {
        return logroRepository.findAllAsDTO();
    }

    @Override
    public Page<LogroUserDTO> findAllAsDTO(Pageable pageable) {
        return logroRepository.findAllAsDTO(pageable);
    }

    @Override
    public Optional<LogroUserDTO> findDTOById(Long id) {
        return logroRepository.findDTOById(id);
    }

    @Override
    public Optional<Logro> findById(Long id) {
        return logroRepository.findById(id);
    }

    @Override
    public List<LogroUserDTO> findByName(String name) {
        return logroRepository.findByName(name);
    }

    @Override
    public List<LogroUserDTO> findByUsuariosId(Long usuarioId) {
        return logroRepository.findByUsuariosId(usuarioId);
    }
    
    @Override
    public List<LogroUserDTO> findByDescripcionContainingIgnoreCase(String descripcion) {
        return logroRepository.findByDescripcionContainingIgnoreCase(descripcion);
    }
    @Override
    public Logro createLogro(Logro logro) {
        return logroRepository.save(logro);
    }

    @Override
    public Logro updateLogro(Long id, Logro logro) {
        return logroRepository.findById(id).map(existingLogro -> {
            existingLogro.setDescripcion(logro.getDescripcion());
            existingLogro.setIcono(logro.getIcono());
            existingLogro.setName(logro.getName());
            existingLogro.setUsuarios(logro.getUsuarios());

            return logroRepository.save(existingLogro);
        }).orElseThrow(() -> new RuntimeException("Logro no encontrado"));
    }

    @Override
    public void deleteLogro(Long id) {
        logroRepository.deleteById(id);
    }
}
