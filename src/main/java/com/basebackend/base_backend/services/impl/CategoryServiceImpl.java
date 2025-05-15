package com.basebackend.base_backend.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basebackend.base_backend.entities.Categoria;
import com.basebackend.base_backend.entities.DTO.CategoryUserDTO;
import com.basebackend.base_backend.entities.DTO.EventoDTO;
import com.basebackend.base_backend.repositories.CategoryRepository;
import com.basebackend.base_backend.services.CategoryService;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Categoria> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CategoryUserDTO> findAllAsDTO() {
        return categoryRepository.findAllAsDTO();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryUserDTO> findAllAsDTO(Pageable pageable) {
        return categoryRepository.findAllAsDTO(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<CategoryUserDTO> findDTOById(Long id) {
        return categoryRepository.findDTOById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Categoria> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryUserDTO> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryUserDTO> findByDescripcionContainingIgnoreCase(String description) {
        return categoryRepository.findByDescripcionContainingIgnoreCase(description);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryUserDTO> findByNameContainigngIgnoreCase(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventoDTO> getEventosByCategoria(Long idCategoria) {
        Optional<Categoria> categoria = categoryRepository.findById(idCategoria);
        return categoria.map(c -> c.getEventos().stream()
                .map(evento -> {
                    EventoDTO dto = new EventoDTO(
                            evento.getId(),
                            evento.getName(),
                            evento.getDescripcion(),
                            evento.getEstado() != null ? evento.getEstado().getId() : null,
                            evento.getEstado() != null ? evento.getEstado().getName() : null,
                            evento.getfCreacion(),
                            evento.getfInicio(),
                            evento.getfTopeInscripcion(),
                            evento.getfFin(),
                            evento.getFeed() != null ? evento.getFeed().getId() : null,
                            evento.getCategoria() != null ? evento.getCategoria().getId() : null,
                            evento.getCategoria() != null ? evento.getCategoria().getName() : null,
                            evento.getCodigoVestimenta(),
                            evento.getCoste(),
                            evento.getDislikes(),
                            evento.getLikes(),
                            evento.getLimiteUsuarios(),
                            evento.getLinkExterno(),
                            evento.getUbicacion(),
                            evento.getUsuario() != null ? evento.getUsuario().getId() : null,
                            evento.getUsuario() != null ? evento.getUsuario().getUserName() : null
                    );

                    dto.setUsuarios(
                            evento.getUsuarios() != null ? evento.getUsuarios().stream().map(u -> u.getId()).toList() : Collections.emptyList()
                    );
                    dto.setSeguidores(
                            evento.getSeguidores() != null ? evento.getSeguidores().stream().map(u -> u.getId()).toList() : Collections.emptyList()
                    );

                    return dto;
                })
                .toList())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    }

    @Override
    public Categoria createCategory(Categoria categoria) {
        return categoryRepository.save(categoria);
    }

    @Override
    public Categoria updateCategory(Long id, Categoria categoria) {
        return categoryRepository.findById(id).map(existingCategory -> {
            existingCategory.setName(categoria.getName());
            existingCategory.setDescripcion(categoria.getDescripcion());
            existingCategory.setEventos(categoria.getEventos());
            existingCategory.setSeguidores(categoria.getSeguidores());

            return categoryRepository.save(existingCategory);
        }).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

}
