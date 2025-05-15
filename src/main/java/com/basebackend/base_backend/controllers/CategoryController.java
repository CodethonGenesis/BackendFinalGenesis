package com.basebackend.base_backend.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basebackend.base_backend.entities.Categoria;
import com.basebackend.base_backend.entities.DTO.CategoryUserDTO;
import com.basebackend.base_backend.entities.DTO.EventoDTO;
import com.basebackend.base_backend.services.impl.CategoryServiceImpl;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/categorias")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity <List<CategoryUserDTO>> getAllCategories() {
        List<CategoryUserDTO> categories = categoryService.findAllAsDTO();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/page/{page},{pageSize}")
    public ResponseEntity<List<CategoryUserDTO>> listPageable(@PathVariable Integer page, @PathVariable Integer pageSize) {
        try {
            PageRequest pageable = PageRequest.of(page, pageSize);
            List<CategoryUserDTO> result = categoryService.findAllAsDTO(pageable).getContent();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id){
        Optional<Categoria> optional = categoryService.findById(id);

        if(optional.isPresent()){
            return ResponseEntity.ok(optional.orElseThrow());
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentran categorias con este id " + id));
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<?> getCategoryDtoById(@PathVariable Long id){
        Optional<CategoryUserDTO> optional = categoryService.findDTOById(id);

        if(optional.isPresent()){
            return ResponseEntity.ok(optional.orElseThrow());
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentran categorias con este id " + id));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String name){
        List<CategoryUserDTO> optional = categoryService.findByName(name);

        if(!optional.isEmpty()){
            return ResponseEntity.ok(optional);
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentran categorias con este nombre " + name));
        }
    }

    @GetMapping("/nameContains/{name}")
    public ResponseEntity<?> getCategoryByNameContais(@PathVariable String name){
        List<CategoryUserDTO> optional = categoryService.findByNameContainigngIgnoreCase(name);

        if(!optional.isEmpty()){
            return ResponseEntity.ok(optional);
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentran categorias con este nombre " + name));
        }
    }

    @GetMapping("/eventosPorCategoria/{idCategoria}")
    public ResponseEntity<?> getEventosByCategoria(@PathVariable Long idCategoria){
        List<EventoDTO> optional = categoryService.getEventosByCategoria(idCategoria);

        if(!optional.isEmpty()){
            return ResponseEntity.ok(optional);
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentran eventos con esta categoria " + idCategoria));
        }
    }

    @GetMapping("/descripcion/{descripcion}")
    public ResponseEntity<?> getCategoryDescripcionContainingIgnoreCase(@PathVariable String descripcion){
        List<CategoryUserDTO> optional = categoryService.findByDescripcionContainingIgnoreCase(descripcion);

        if(!optional.isEmpty()){
            return ResponseEntity.ok(optional);
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentran categorias con esta descripcion " + descripcion));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Categoria categoria, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Categoria categoria, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validation(result);
        }

        Categoria updatedCategory = categoryService.updateCategory(id, categoria);
        Optional<Categoria> categoryOptional = Optional.ofNullable(updatedCategory);

        if (categoryOptional.isPresent()) {
            return ResponseEntity.ok(categoryOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo: " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Categoria> catOptional = categoryService.findById(id);

        if(catOptional.isPresent()){
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
