package com.basebackend.base_backend.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basebackend.base_backend.entities.Categoria;
import com.basebackend.base_backend.entities.Comentario;
import com.basebackend.base_backend.entities.DTO.UsuarioDTO;
import com.basebackend.base_backend.entities.Evento;
import com.basebackend.base_backend.entities.Logro;
import com.basebackend.base_backend.entities.Preferencias;
import com.basebackend.base_backend.entities.Rol;
import com.basebackend.base_backend.entities.Usuario;
import com.basebackend.base_backend.models.UserRequest;
import com.basebackend.base_backend.repositories.CategoryRepository;
import com.basebackend.base_backend.repositories.EventoRepository;
import com.basebackend.base_backend.repositories.LogroRepository;
import com.basebackend.base_backend.repositories.RolRepository;
import com.basebackend.base_backend.repositories.UsuarioRepository;
import com.basebackend.base_backend.services.UserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
    @PersistenceContext
    private EntityManager entityManager;

    private final UsuarioRepository repository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final EventoRepository eventoRepository;
    private final LogroRepository logroRepository;
    private final CategoryRepository categoriaRepository;

    public UserServiceImpl(UsuarioRepository repository, RolRepository rolRepository, PasswordEncoder passwordEncoder,
            EventoRepository eventoRepository, LogroRepository logroRepository,
            CategoryRepository categoriaRepository) {
        this.repository = repository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventoRepository = eventoRepository;
        this.logroRepository = logroRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = repository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s does not exist", username)));

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                true, true, true, true,
                authorities
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAllAsDTO() {
        List<UsuarioDTO> usuarios = repository.findAllAsDTO();
        usuarios.forEach(this::setAdditionalUserData);  // Set additional data for each user DTO
        return usuarios;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findAllAsDTO(Pageable pageable) {
        Page<UsuarioDTO> usuarios = repository.findAllAsDTO(pageable);
        usuarios.forEach(this::setAdditionalUserData);  // Set additional data for each user DTO
        return usuarios;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findDTOById(@NonNull Long id) {
        Optional<UsuarioDTO> usuario = repository.findDTOById(id);
        usuario.ifPresent(this::setAdditionalUserData);  // Set additional data
        return usuario;
    }

    @Override
    @Transactional
    public Usuario save(Usuario user) {
        user.setRoles(getRoles(user));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    @Transactional
    public Optional<Usuario> update(UserRequest userRequest, Long id) {
        Optional<Usuario> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {
            Usuario userDb = userOptional.get();

            userDb.setEmail(userRequest.getEmail());
            userDb.setLastName(userRequest.getLastname());
            userDb.setName(userRequest.getName());
            userDb.setUserName(userRequest.getUsername());
            userDb.setTipoDocumento(userRequest.getTipoDocumento());
            userDb.setDocumento(userRequest.getDocumento());
            
            Preferencias preferencias = userRequest.getSelfPreferencias() != null
                    ? entityManager.find(Preferencias.class, userRequest.getSelfPreferencias())
                    : null;

            if (preferencias != null) {
                preferencias = entityManager.merge(preferencias);
            } else {
                preferencias = new Preferencias();
            }

            userDb.setSelfPreferencias(preferencias);
            userDb.setRegistro(userRequest.getRegistro());

            // Обновляем события и другие связи
            updateEventos(userDb, userRequest);
            updateLogros(userDb, userRequest);
            updateCategorias(userDb, userRequest);
            updateUsuariosSeguidos(userDb, userRequest);

            userDb.setRoles(getRoles(userDb)); 

            return Optional.of(repository.save(userDb));
        }

        return Optional.empty();
    }

    private void updateEventos(Usuario userDb, UserRequest userRequest) {
        if (userRequest.getEventosCreados() != null) {
            userDb.getEventosCreados().clear();
            for (Long eventoId : userRequest.getEventosCreados()) {
            Optional<Evento> evento = eventoRepository.findById(eventoId);
            evento.ifPresent(e -> {
                e.setUsuario(userDb);
                userDb.getEventosCreados().add(e);
            });
            }
        }

        if (userRequest.getEventosApuntados() != null) {
            userDb.getEventosApuntados().clear();
            for (Long eventoId : userRequest.getEventosApuntados()) {
                Optional<Evento> evento = eventoRepository.findById(eventoId);
                evento.ifPresent(userDb.getEventosApuntados()::add);
            }
        }

        if (userRequest.getEventosSeguidos() != null) {
            userDb.getEventosSeguidos().clear();
            for (Long eventoId : userRequest.getEventosSeguidos()) {
                Optional<Evento> evento = eventoRepository.findById(eventoId);
                evento.ifPresent(userDb.getEventosSeguidos()::add);
            }
        }
    }

    private void updateLogros(Usuario userDb, UserRequest userRequest) {
        if (userRequest.getLogros() != null) {
            userDb.getLogros().clear();
            for (Long logroId : userRequest.getLogros()) {
                Optional<Logro> logro = logroRepository.findById(logroId);
                logro.ifPresent(userDb.getLogros()::add);
            }
        }
    }

    private void updateCategorias(Usuario userDb, UserRequest userRequest) {
        if (userRequest.getCategorias() != null) {
            userDb.getCategorias().clear();
            for (Long categoriaId : userRequest.getCategorias()) {
                Optional<Categoria> categoria = categoriaRepository.findById(categoriaId);
                categoria.ifPresent(userDb.getCategorias()::add);
            }
        }
    }

    private void updateUsuariosSeguidos(Usuario userDb, UserRequest userRequest) {
        if (userRequest.getUsuariosSeguidos() != null) {
            userDb.getUsuariosSeguidos().clear();
            for (Long seguidoId : userRequest.getUsuariosSeguidos()) {
                Optional<Usuario> seguido = repository.findById(seguidoId);
                seguido.ifPresent(userDb.getUsuariosSeguidos()::add);
            }
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private List<Rol> getRoles(Usuario user) {
        List<Rol> roles = new ArrayList<>();
        Optional<Rol> optionalRoleUser = rolRepository.findByName("ROLE_USER");
        optionalRoleUser.ifPresent(roles::add);

        return roles;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findDTOByUserName(String userName) {
        Optional<UsuarioDTO> usuarioDTO = repository.findDTOByUserName(userName);
        usuarioDTO.ifPresent(this::setAdditionalUserData);  // Set additional data
        return usuarioDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findDTOByEmail(String email) {
        Optional<UsuarioDTO> usuarioDTO = repository.findDTOByEmail(email);
        usuarioDTO.ifPresent(this::setAdditionalUserData);  // Set additional data
        return usuarioDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findDTOByUserNameContaining(String userName) {
        Optional<UsuarioDTO> usuarioDTO = repository.findDTOByUserNameContaining(userName);
        usuarioDTO.ifPresent(this::setAdditionalUserData);  // Set additional data
        return usuarioDTO;
    }

    private void setAdditionalUserData(UsuarioDTO usuarioDTO) {
        setEventoCreadosToUserDTO(usuarioDTO);
        setEventosApuntadosToUserDTO(usuarioDTO);
        setEventosSeguidosToUserDTO(usuarioDTO);
        setUsuariosSeguidosToUserDTO(usuarioDTO);
        setSeguidoresToUserDTO(usuarioDTO);
        setComentariosToUserDTO(usuarioDTO);
        setCategoriasToUserDTO(usuarioDTO);
        setRolesToUserDTO(usuarioDTO);
        setLogrosToUserDTO(usuarioDTO);
    }

    private void setEventoCreadosToUserDTO(UsuarioDTO userDTO) {
        Optional<Usuario> user = repository.findById(userDTO.getId());
        user.ifPresent(u -> userDTO.setEventosCreados(u.getEventosCreados().stream().map(Evento::getId).toList()));
    }

    private void setEventosApuntadosToUserDTO(UsuarioDTO userDTO) {
        Optional<Usuario> user = repository.findById(userDTO.getId());
        user.ifPresent(u -> userDTO.setEventosApuntados(u.getEventosApuntados().stream().map(Evento::getId).toList()));
    }

    private void setEventosSeguidosToUserDTO(UsuarioDTO userDTO) {
        Optional<Usuario> user = repository.findById(userDTO.getId());
        user.ifPresent(u -> userDTO.setEventosSeguidos(u.getEventosSeguidos().stream().map(Evento::getId).toList()));
    }

    private void setUsuariosSeguidosToUserDTO(UsuarioDTO userDTO) {
        Optional<Usuario> user = repository.findById(userDTO.getId());
        user.ifPresent(u -> userDTO.setUsuariosSeguidos(u.getUsuariosSeguidos().stream().map(Usuario::getId).toList()));
    }

    private void setSeguidoresToUserDTO(UsuarioDTO userDTO) {
        Optional<Usuario> user = repository.findById(userDTO.getId());
        user.ifPresent(u -> userDTO.setSeguidores(u.getSeguidores().stream().map(Usuario::getId).toList()));
    }

    private void setComentariosToUserDTO(UsuarioDTO userDTO) {
        Optional<Usuario> user = repository.findById(userDTO.getId());
        user.ifPresent(u -> userDTO.setComentarios(u.getComentarios().stream().map(Comentario::getId).toList()));
    }

    private void setCategoriasToUserDTO(UsuarioDTO userDTO) {
        Optional<Usuario> user = repository.findById(userDTO.getId());
        user.ifPresent(u -> userDTO.setCategorias(u.getCategorias().stream().map(Categoria::getId).toList()));
    }

    private void setRolesToUserDTO(UsuarioDTO userDTO) {
        Optional<Usuario> user = repository.findById(userDTO.getId());
        user.ifPresent(u -> userDTO.setRoles(u.getRoles().stream().map(Rol::getId).toList()));
    }

    private void setLogrosToUserDTO(UsuarioDTO userDTO) {
        Optional<Usuario> user = repository.findById(userDTO.getId());
        user.ifPresent(u -> userDTO.setLogros(u.getLogros().stream().map(Logro::getId).toList()));
    }
}
