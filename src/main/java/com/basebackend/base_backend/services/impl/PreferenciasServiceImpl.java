package com.basebackend.base_backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basebackend.base_backend.entities.Preferencias;
import com.basebackend.base_backend.entities.enums.Tema;
import com.basebackend.base_backend.repositories.PreferenciasRepository;
import com.basebackend.base_backend.services.PreferenciasService;

@Service("PreferenciasService")
public class PreferenciasServiceImpl implements PreferenciasService{

    private final PreferenciasRepository repository;

    
    public PreferenciasServiceImpl(PreferenciasRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Preferencias> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Preferencias> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Preferencias> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Preferencias> findByTema(Tema tema) {
        return repository.findByTema(tema);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Preferencias> findByDaltonico(Boolean daltonico) {
        return repository.findByDaltonico(daltonico);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Preferencias> findByDiscapacidadVisual(Boolean discapacidadVisual) {
        return repository.findByDiscapacidadVisual(discapacidadVisual);
    }

    @Override
    public Optional<Preferencias> update(Preferencias preferencias, Long id) {
        Optional<Preferencias> preferenciasOptional = repository.findById(id);
        
        if(preferenciasOptional.isPresent()){
            Preferencias prefDb = preferenciasOptional.get();
            prefDb.setDaltonico(preferencias.getDaltonico());
            prefDb.setDiscapacidadVisual(preferencias.getDiscapacidadVisual());
            prefDb.setTema(preferencias.getTema());
            prefDb.setTextSize(preferencias.getTextSize());
            prefDb.setIdioma(preferencias.getIdioma());

            return Optional.of(repository.save(prefDb));
        }

        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Preferencias createPrefencias(Preferencias preferencias) {
        return repository.save(preferencias);
    }
}
