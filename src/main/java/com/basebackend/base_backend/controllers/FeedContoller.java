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

import com.basebackend.base_backend.entities.DTO.FeedDTO;
import com.basebackend.base_backend.entities.Feed;
import com.basebackend.base_backend.services.impl.FeedServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/feed")
public class FeedContoller {
    
    @Autowired
    private FeedServiceImpl feedService;

    @GetMapping 
    public List<FeedDTO> getAllFeed(){
        return feedService.findAllAsDTO();
    }

    @GetMapping("/page/{page},{pageSize}")
    public ResponseEntity<List<FeedDTO>> listPageable(@PathVariable Integer page, @PathVariable Integer pageSize) {
        try {
            PageRequest pageable = PageRequest.of(page, pageSize);
            List<FeedDTO> result = feedService.findAllAsDTO(pageable).getContent();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getFeedById(@PathVariable Long id){
        Optional<FeedDTO> optional = feedService.findDTOById(id);

        if(optional.isPresent()){
            return ResponseEntity.ok(optional.orElseThrow());
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentran feeds con este id " + id));
        }
    }

    @GetMapping("/evento/{IdEvento}")
    public ResponseEntity<?> getFeedByEvento(@PathVariable Long IdEvento){
        Optional<FeedDTO> optional = feedService.findDTOByEvento(IdEvento);

        if(optional.isPresent()){
            return ResponseEntity.ok(optional.orElseThrow());
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentran feeds con este evento " + IdEvento));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Feed feed, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(feedService.create(feed));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Feed feed, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<Feed> feedOptional = feedService.update(feed, id);

        if (feedOptional.isPresent()) {
            return ResponseEntity.ok(feedOptional.orElseThrow());
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
        Optional<FeedDTO> feedOpt = feedService.findDTOById(id);

        if(feedOpt.isPresent()){
            feedService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
