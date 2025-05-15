package com.basebackend.base_backend.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.basebackend.base_backend.entities.VerificationCode;
import com.basebackend.base_backend.services.impl.VerificationCodeServiceImpl;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/verificationcodes")
public class VerificationCodeController {
    
    @Autowired
    private VerificationCodeServiceImpl verificationCodeService;

    @GetMapping
    public List<VerificationCode> getAllVerificationCodes(){
        return verificationCodeService.findAll();
    }
    
    @GetMapping("/page/{page}")
    public Page<VerificationCode> listPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 4);
        return verificationCodeService.findAll(pageable);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getCodeById(@PathVariable Long id){
        Optional<VerificationCode> optional = verificationCodeService.findById(id);
        
        if(optional.isPresent()){
            return ResponseEntity.ok(optional.orElseThrow());
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentra verification code con este id " + id));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getCodeByEmail(@PathVariable String email){
        Optional<VerificationCode> optional = verificationCodeService.findByEmail(email);
        
        if(optional.isPresent()){
            return ResponseEntity.ok(optional.orElseThrow());
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentra verification code con este email " + email));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<VerificationCode> userOptional = verificationCodeService.findById(id);
        if (userOptional.isPresent()) {

            verificationCodeService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody VerificationCode code, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(verificationCodeService.createCode(code));
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo: " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
