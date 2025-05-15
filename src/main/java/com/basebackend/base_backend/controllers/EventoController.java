package com.basebackend.base_backend.controllers;

import java.time.Instant;
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
import com.basebackend.base_backend.entities.DTO.EventoDTO;
import com.basebackend.base_backend.entities.Estado;
import com.basebackend.base_backend.entities.Evento;
import com.basebackend.base_backend.entities.Usuario;
import com.basebackend.base_backend.services.impl.CategoryServiceImpl;
import com.basebackend.base_backend.services.impl.EstadoServiceImpl;
import com.basebackend.base_backend.services.impl.EventoServiceImpl;
import com.basebackend.base_backend.services.impl.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoServiceImpl eventoService;

    private final EstadoServiceImpl estadoService;

    private final CategoryServiceImpl categoryService;

    private final UserServiceImpl userService;

    public EventoController(EventoServiceImpl eventoService, EstadoServiceImpl estadoService, CategoryServiceImpl categoryService, UserServiceImpl userService) {
        this.userService = userService;
        this.eventoService = eventoService;
        this.estadoService = estadoService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<EventoDTO> getAllEventos() {
        return eventoService.findAllAsDTO();
    }

    @GetMapping("/page/{page},{pageSize}")
    public ResponseEntity<List<EventoDTO>> listPageable(@PathVariable Integer page, @PathVariable Integer pageSize) {
        try {
            PageRequest pageable = PageRequest.of(page, pageSize);
            List<EventoDTO> result = eventoService.findAllAsDTO(pageable).getContent();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventoById(@PathVariable Long id) {
        Optional<Evento> optional = eventoService.findById(id);

        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.orElseThrow());
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentra evento con este id " + id));
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getEventoDTOById(@PathVariable Long id) {
        Optional<EventoDTO> optional = eventoService.findDTOById(id);

        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.orElseThrow());
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentra evento con este id " + id));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getEventoByName(@PathVariable String name) {
        Optional<EventoDTO> optional = eventoService.findDTOByName(name);

        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.orElseThrow());
        } else {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "No se encuentra evento con este nombre " + name));
        }
    }
    
    @GetMapping("/estado/{estadoId}")
    public List<EventoDTO> getEventoByEstado(@PathVariable Long estadoId){
        return eventoService.findDTOByEstado(estadoId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<EventoDTO> eventoOptional = eventoService.findDTOById(id);

        if (eventoOptional.isPresent()) {
            eventoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Evento evento, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.createEvento(evento));

    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo: " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Evento evento, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<Evento> eventoOptional = eventoService.update(evento, id);

        if (eventoOptional.isPresent()) {
            return ResponseEntity.ok(eventoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/with-dto")
    public ResponseEntity<?> createWithDto(@Valid @RequestBody EventoDTO eventodto, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }

        Categoria categoria = categoryService.findById(eventodto.getCategoria_id())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + eventodto.getCategoria_id()));

        Estado estado = estadoService.findById(eventodto.getEstado_id())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con id: " + eventodto.getEstado_id()));

        Usuario user = userService.findById(eventodto.getUsuario_id())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + eventodto.getUsuario_id()));

        Evento eventoEntity = new Evento();
        eventoEntity.setName(eventodto.getName());
        eventoEntity.setDescripcion(eventodto.getDescripcion());
        eventoEntity.setEstado(estado);
        eventoEntity.setfCreacion(eventodto.getfCreacion());
        eventoEntity.setfInicio(eventodto.getfInicio());
        eventoEntity.setfFin(eventodto.getfFin());
        eventoEntity.setfTopeInscripcion(eventodto.getfTopeInscripcion());
        eventoEntity.setCategoria(categoria);
        eventoEntity.setUsuario(user);
        eventoEntity.setLinkExterno(eventodto.getLinkExterno());
        eventoEntity.setUbicacion(eventodto.getUbicacion());   
        eventoEntity.setLimiteUsuarios(eventodto.getLimiteUsuarios());
        eventoEntity.setCoste(eventodto.getCoste());
        eventoEntity.setCodigoVestimenta(eventodto.getCodigoVestimenta());

    
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.createEvento(eventoEntity));
    }

      @GetMapping("/usuario/{userId}")
    public ResponseEntity<?> getEventosByUsuarioId(@PathVariable Long userId) {
        try {
            List<EventoDTO> eventos = eventoService.findEventosByUsuarioId(userId);
            
            if (eventos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            
            return ResponseEntity.ok(eventos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los eventos del usuario: " + e.getMessage());
        }
    }

      @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<?> getEventosByCategoriaId(@PathVariable Long categoriaId) {
        try {
            List<EventoDTO> eventos = eventoService.findEventosByCategoriaId(categoriaId);
            
            if (eventos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            
            return ResponseEntity.ok(eventos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los eventos de la categoría: " + e.getMessage());
        }
    }
      @GetMapping("/apuntado/{usuarioId}")
    public ResponseEntity<?> getEventosByApuntado(@PathVariable Long usuarioId) {
        try {
            List<EventoDTO> eventos = eventoService.findEventosApuntadosByUserId(usuarioId);
            
            if (eventos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            
            return ResponseEntity.ok(eventos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los eventos de la categoría: " + e.getMessage());
        }
    }

@PostMapping("/{eventoId}/apuntarse/{usuarioId}")
public ResponseEntity<?> apuntarseEvento(@PathVariable Long eventoId, @PathVariable Long usuarioId) {
    try {
        // Buscar el evento y el usuario
        Evento evento = eventoService.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con id: " + eventoId));
        
        Usuario usuario = userService.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));
        
        // Verificar si el usuario ya está apuntado al evento
        if (evento.getUsuarios().stream().anyMatch(u -> u.getId().equals(usuarioId))) {
            return ResponseEntity.badRequest().body(
                Collections.singletonMap("error", "El usuario ya está apuntado a este evento")
            );
        }
        
        // Verificar si el evento ha alcanzado su límite de usuarios
        if (evento.getLimiteUsuarios() != null && evento.getUsuarios().size() >= evento.getLimiteUsuarios()) {
            return ResponseEntity.badRequest().body(
                Collections.singletonMap("error", "El evento ha alcanzado su límite de participantes")
            );
        }
        
        // Verificar si la fecha tope de inscripción ha pasado
        if (evento.getfTopeInscripcion() != null && Instant.now().isAfter(evento.getfTopeInscripcion())) {
            return ResponseEntity.badRequest().body(
                Collections.singletonMap("error", "La fecha límite de inscripción ha pasado")
            );
        }
        
        // Utilizamos el servicio para manejar la relación
        eventoService.apuntarUsuarioAEvento(evento, usuario);
        
        return ResponseEntity.ok(
            Collections.singletonMap("message", "Usuario apuntado correctamente al evento")
        );
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            Collections.singletonMap("error", e.getMessage())
        );
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            Collections.singletonMap("error", "Error al apuntar usuario al evento: " + e.getMessage())
        );
    }
}

@DeleteMapping("/{eventoId}/desapuntarse/{usuarioId}")
public ResponseEntity<?> desapuntarseEvento(@PathVariable Long eventoId, @PathVariable Long usuarioId) {
    try {
        // Buscar el evento y el usuario
        Evento evento = eventoService.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con id: " + eventoId));
        
        Usuario usuario = userService.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));
        
        // Verificar si el usuario está apuntado al evento
        if (evento.getUsuarios().stream().noneMatch(u -> u.getId().equals(usuarioId))) {
            return ResponseEntity.badRequest().body(
                Collections.singletonMap("error", "El usuario no está apuntado a este evento")
            );
        }
        
        // Utilizamos el servicio para manejar la relación
        eventoService.desapuntarUsuarioDeEvento(evento, usuario);
        
        return ResponseEntity.ok(
            Collections.singletonMap("message", "Usuario desapuntado correctamente del evento")
        );
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            Collections.singletonMap("error", e.getMessage())
        );
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            Collections.singletonMap("error", "Error al desapuntar usuario del evento: " + e.getMessage())
        );
    }

}
}
