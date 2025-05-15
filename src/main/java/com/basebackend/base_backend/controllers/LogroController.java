package com.basebackend.base_backend.controllers;

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

import com.basebackend.base_backend.entities.DTO.LogroUserDTO;
import com.basebackend.base_backend.entities.Logro;
import com.basebackend.base_backend.services.LogroService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/logros")
public class LogroController {

    private final LogroService logroService;

    public LogroController(LogroService logroService) {
        this.logroService = logroService;
    }

    @GetMapping
    public ResponseEntity<?> getAllLogros() {
        List<LogroUserDTO> logros = logroService.findAllAsDTO();
        return ResponseEntity.ok(logros);
    }

    @GetMapping("/page/{page},{pageSize}")
    public ResponseEntity<List<LogroUserDTO>> listPageable(@PathVariable Integer page, @PathVariable Integer pageSize) {
        try {
            List<LogroUserDTO> result = logroService.findAllAsDTO(PageRequest.of(page, pageSize)).getContent();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getLogroById(@PathVariable Long id) {
        Optional<LogroUserDTO> optional = logroService.findDTOById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.orElseThrow());
        } else {
            return ResponseEntity.status(404).body("No se encuentran logros con este id " + id);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getLogroByName(@PathVariable String name) {
        List<LogroUserDTO> optional = logroService.findByName(name);
        if (!optional.isEmpty()) {
            return ResponseEntity.ok(optional);
        } else {
            return ResponseEntity.status(404).body("No se encuentran logros con este nombre " + name);
        }
    }

    @GetMapping("/descripcion/{descripcion}")
    public ResponseEntity<?> getLogroByDescripcion(@PathVariable String descripcion) {
        List<LogroUserDTO> optional = logroService.findByDescripcionContainingIgnoreCase(descripcion);
        if (!optional.isEmpty()) {
            return ResponseEntity.ok(optional);
        } else {
            return ResponseEntity.status(404).body("No se encuentran logros con esta descripcion " + descripcion);
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> getLogroByUsuario(@PathVariable Long idUsuario) {
        List<LogroUserDTO> optional = logroService.findByUsuariosId(idUsuario);
        if (!optional.isEmpty()) {
            return ResponseEntity.ok(optional);
        } else {
            return ResponseEntity.status(404).body("No se encuentran logros con este usuario " + idUsuario);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Logro logro, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(logroService.createLogro(logro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Logro logro, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validation(result);
        }

        Logro updLogro = logroService.updateLogro(id, logro);
        Optional<Logro> logroOptional = Optional.ofNullable(updLogro);

        if (logroOptional.isPresent()) {
            return ResponseEntity.ok(logroOptional.orElseThrow());
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
        Optional<Logro> logroOptional = logroService.findById(id);

        if(logroOptional.isPresent()){
            logroService.deleteLogro(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
    
}
