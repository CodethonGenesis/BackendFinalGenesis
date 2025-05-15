package com.basebackend.base_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.basebackend.base_backend.entities.Categoria;
import com.basebackend.base_backend.entities.DTO.CategoryUserDTO;

@Repository
public interface CategoryRepository extends JpaRepository<Categoria, Long> {

    @Override
    List<Categoria> findAll();
    
    @Query("SELECT new com.basebackend.base_backend.entities.DTO.CategoryUserDTO(c.id, c.name, c.descripcion) FROM Categoria c")
    List<CategoryUserDTO> findAllAsDTO();

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.CategoryUserDTO(c.id, c.name, c.descripcion) FROM Categoria c")
    Page<CategoryUserDTO> findAllAsDTO(Pageable pageable);

    @Query("SELECT new com.basebackend.base_backend.entities.DTO.CategoryUserDTO(c.id, c.name, c.descripcion) FROM Categoria c WHERE c.id = :id")
    Optional<CategoryUserDTO> findDTOById(Long id);

    List<CategoryUserDTO> findByName(String name);

    List<CategoryUserDTO> findByDescripcionContainingIgnoreCase(String description);

    List<CategoryUserDTO> findByNameContainingIgnoreCase(String name);

}
