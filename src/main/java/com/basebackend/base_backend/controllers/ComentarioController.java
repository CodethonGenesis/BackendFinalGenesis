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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basebackend.base_backend.entities.Comentario;
import com.basebackend.base_backend.entities.DTO.CommentDTO;
import com.basebackend.base_backend.repositories.ComentarioRepository;
import com.basebackend.base_backend.services.impl.ComentarioServiceImpl;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {
    
    @Autowired
    private ComentarioServiceImpl comentarioService;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @GetMapping
    public List<CommentDTO> getAllComentarios(){
        return comentarioService.findAllAsDTO();
    }

    @GetMapping("feed/{idFeed}")
    public ResponseEntity<?> getComentariosByFeedId(@PathVariable Long idFeed){
        List<CommentDTO> optional = comentarioRepository.findDTOByFeed(idFeed);

        if(!optional.isEmpty()){
            return ResponseEntity.ok(optional);
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentran comentarios con este id de feed " + idFeed));
        }
    }
    

    @GetMapping("/page/{page},{pageSize}")
    public ResponseEntity<List<CommentDTO>> listPageable(@PathVariable Integer page, @PathVariable Integer pageSize) {
        try {
            PageRequest pageable = PageRequest.of(page, pageSize);
            List<CommentDTO> result = comentarioService.findAllAsDTO(pageable).getContent();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getComentarioById(@PathVariable Long id){
        Optional<CommentDTO> optional = comentarioService.findDTOById(id);

        if(optional.isPresent()){
            return ResponseEntity.ok(optional.orElseThrow());
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentran comentarios con este id " + id));
        }
    }
    
    @GetMapping("/contiene/{contenido}")
    public ResponseEntity<?> getComentarioByContenido(@PathVariable String contenido){
        List<CommentDTO> optional = comentarioService.findDTOByContenidoContainingIgnoreCase(contenido);

        if(!optional.isEmpty()){
            return ResponseEntity.ok(optional);
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentran comentarios con este contenido " + contenido));
        }
    }

    @GetMapping("/autor/{idAutor}")
    public ResponseEntity<?> getComentarioByAutor(@PathVariable Long idAutor){
        List<CommentDTO> optional = comentarioService.findDTOByAutorId(idAutor);

        if(!optional.isEmpty()){
            return ResponseEntity.ok(optional);
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentran comentarios con este autor " + idAutor));
        }
    }

    @GetMapping("/ordenar/{sort}")
    public List<CommentDTO> ordenar(@PathVariable String sort) {
        return switch (sort.toLowerCase()) {
            case "likes_desc" -> comentarioService.findAllDTOByOrderByLikesDesc();
            case "likes_asc" -> comentarioService.findAllDTOByOrderByDislikesAsc();
            case "dislikes_desc" -> comentarioService.findAllDTOByOrderByDislikesDesc();
            case "dislikes_asc" -> comentarioService.findAllDTOByOrderByDislikesAsc();
            default -> comentarioService.findAllAsDTO();
        };
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Comentario comentario, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioService.createComentario(comentario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Comentario comentario, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<Comentario> comentarioOptional = comentarioService.update(comentario, id);

        if (comentarioOptional.isPresent()) {
            return ResponseEntity.ok(comentarioOptional.orElseThrow());
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
