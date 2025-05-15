package com.basebackend.base_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import com.basebackend.base_backend.entities.DTO.UsuarioDTO;
import com.basebackend.base_backend.entities.Usuario;
import com.basebackend.base_backend.models.UserRequest;

public interface UserService {

    List<UsuarioDTO> findAllAsDTO();

    Page<UsuarioDTO> findAllAsDTO(Pageable pageable);

    Optional<UsuarioDTO> findDTOById(@NonNull Long id);

    Optional<Usuario> findById(@NonNull Long id);

    Optional<UsuarioDTO> findDTOByUserName(String userName);

    Optional<Usuario> findByUserName(String userName);

    Optional<UsuarioDTO> findDTOByUserNameContaining(String userName);

    Optional<UsuarioDTO> findDTOByEmail(String email);

    Optional<Usuario> findByEmail(String email);
    
    Usuario save(Usuario user);

    Optional<Usuario> update(UserRequest user, Long id);

    void deleteById(Long id);

}
