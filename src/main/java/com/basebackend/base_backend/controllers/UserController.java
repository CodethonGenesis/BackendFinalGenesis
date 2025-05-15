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

import com.basebackend.base_backend.entities.DTO.UsuarioDTO;
import com.basebackend.base_backend.entities.Usuario;
import com.basebackend.base_backend.models.UserRequest;
import com.basebackend.base_backend.services.impl.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public List<UsuarioDTO> getAllUsers() {
        return userService.findAllAsDTO();
    }

    @GetMapping("/usersByName/{name}")
    public Optional<UsuarioDTO> getUsersByName(@PathVariable String name) {
        return userService.findDTOByUserName(name);
    }

    @GetMapping("/page/{page},{pageSize}")
    public ResponseEntity<List<UsuarioDTO>> listPageable(@PathVariable Integer page, @PathVariable Integer pageSize) {
        try {
            PageRequest pageable = PageRequest.of(page, pageSize);
            List<UsuarioDTO> result = userService.findAllAsDTO(pageable).getContent();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/userPass/{userName}")
    public ResponseEntity<?> getUserPassword(@PathVariable String userName) {
        Optional<Usuario> userOptional = userService.findByUserName(userName);
        if (userOptional.isPresent()) {
            Usuario user = userOptional.orElseThrow();
            return ResponseEntity.ok(Collections.singletonMap("password", user.getPassword()));
        } else {
            return ResponseEntity.status(404)
                    .body(Collections.singletonMap("error", "Usuario no encontrado con el nombre: " + userName));
        }
    }

    @GetMapping("/userName/{userName}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String userName) {
        Optional<UsuarioDTO> optional = userService.findDTOByUserName(userName);

        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.orElseThrow());
        } else {
            return ResponseEntity.status(404)
                    .body(Collections.singletonMap("error", "No se encuentra el usuario por el nombre de usuario: " + userName));
        }
    }

    @GetMapping("/userNameContaining/{userName}")
    public ResponseEntity<?> getUserByUserNameContaining(@PathVariable String userName) {
        Optional<UsuarioDTO> optional = userService.findDTOByUserNameContaining(userName);

        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.orElseThrow());
        } else {
            return ResponseEntity.status(404)
                    .body(Collections.singletonMap("error", "No se encuentra el usuario por el nombre de usuario: " + userName));
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUserByIdDTO(@PathVariable Long id) {
        Optional<UsuarioDTO> optional = userService.findDTOById(id);

        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.orElseThrow());
        } else {
            return ResponseEntity.status(404)
                    .body(Collections.singletonMap("error", "No se encuentra el usuario por el Id" + id));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Optional<UsuarioDTO> optional = userService.findDTOByEmail(email);

        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.orElseThrow());
        } else {
            return ResponseEntity.status(404)
                    .body(Collections.singletonMap("error", "No se encuentra el usuario por el email: " + email));
        }
    }


    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Usuario user, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UserRequest user, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<Usuario> userOptional = userService.update(user, id);

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Usuario> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {

            userService.deleteById(id);
            return ResponseEntity.noContent().build();
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

    @GetMapping("/email-exists/{email}")
    public Boolean isEmailExist(@PathVariable String email) {
        return userService.findDTOByEmail(email).isPresent();
    }
}
