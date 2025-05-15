package com.basebackend.base_backend.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.basebackend.base_backend.entities.Estado;
import com.basebackend.base_backend.services.impl.EstadoServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/estados")
public class EstadoController {

    @Autowired
    private EstadoServiceImpl estadoService;

    @GetMapping
    public List<Estado> getAllEstados() {
        return estadoService.findAll();
    }

    @GetMapping("/page/{page},{pageSize}")
    public ResponseEntity<List<Estado>> listPageable(@PathVariable Integer page, @PathVariable Integer pageSize) {
        try {
            PageRequest pageable = PageRequest.of(page, pageSize);
            List<Estado> result = estadoService.findAll(pageable).getContent();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEstadoById(@PathVariable Long id) {
        Optional<Estado> optional = estadoService.findById(id);

        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.orElseThrow());
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentra estado con este id " + id));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Estado> estadoOptional = estadoService.findById(id);

        if(estadoOptional.isPresent()){
            estadoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping 
    public ResponseEntity<?> create(@Valid @RequestBody Estado estado, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(estadoService.createEstado(estado));
        
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo: " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Estado estado, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<Estado> estadoOptional = estadoService.update(estado, id);

        if (estadoOptional.isPresent()) {
            return ResponseEntity.ok(estadoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
}
