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

import com.basebackend.base_backend.entities.Preferencias;
import com.basebackend.base_backend.entities.enums.Tema;
import com.basebackend.base_backend.services.impl.PreferenciasServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/preferencias")
public class PreferenciasController {
    
    @Autowired
    private PreferenciasServiceImpl preferenciasService;

    @GetMapping
    public List<Preferencias> getAllPreferencias(){
        return preferenciasService.findAll();
    }

    @GetMapping("/page/{page},{pageSize}")
    public ResponseEntity<List<Preferencias>> listPageable(@PathVariable Integer page, @PathVariable Integer pageSize) {
        try {
            PageRequest pageable = PageRequest.of(page, pageSize);
            List<Preferencias> result = preferenciasService.findAll(pageable).getContent();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("id/{id}")
    public ResponseEntity<?> getEventoById(@PathVariable Long id){
        Optional<Preferencias> optional = preferenciasService.findById(id);

        if(optional.isPresent()){
            return ResponseEntity.ok(optional.orElseThrow());
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentran preferencias con este id " + id));
        }
    }
    
    @GetMapping("/tema/{tema}")
    public ResponseEntity<?> getPreferenciasByTema(@PathVariable Tema tema){
        List<Preferencias> preferencias = preferenciasService.findByTema(tema);
        if(!preferencias.isEmpty()){
            return ResponseEntity.ok(preferencias);
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentran preferencias con este tema " + tema));
        }
    }

    @GetMapping("/daltonico/{daltonico}")
    public ResponseEntity<?> getPreferenciasByDaltonic(@PathVariable Boolean daltonico){
        List<Preferencias> preferencias = preferenciasService.findByDaltonico(daltonico);
        if(!preferencias.isEmpty()){
            return ResponseEntity.ok(preferencias);
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentran preferencias daltonicas " + daltonico));
        }
    }
    @GetMapping("/discapacidadVisual/{discapacidadVisual}")
    public ResponseEntity<?> getPreferenciasByDescapacidadVisual(@PathVariable Boolean discapacidadVisual){
        List<Preferencias> preferencias = preferenciasService.findByDiscapacidadVisual(discapacidadVisual);
        if(!preferencias.isEmpty()){
            return ResponseEntity.ok(preferencias);
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentran preferencias con discapacidad visual " + discapacidadVisual));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Preferencias> preferenciasOptional = preferenciasService.findById(id);

        if(preferenciasOptional.isPresent()){
            preferenciasService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentran preferencias con este id " + id));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Preferencias preferencias, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(preferenciasService.createPrefencias(preferencias));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Preferencias preferencias, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<Preferencias> preferenciasOptional = preferenciasService.update(preferencias, id);

        if (preferenciasOptional.isPresent()) {
            return ResponseEntity.ok(preferenciasOptional.orElseThrow());
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
}
