package com.basebackend.base_backend.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.basebackend.base_backend.entities.Usuario;

/**
 * Spring Data SQL repository for the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {
    /**
     * Retrieves a Page of users.
     * 
     * @param pageable the pagination information.
     * @return a page of users.
     */
    @Override
    @NonNull
    Page<Usuario> findAll(@NonNull Pageable pageable);
    
    /**
     * Find a user by their userName.
     * 
     * @param userName the userName to search for
     * @return the user if found
     */
    Optional<Usuario> findByUserName(String userName);

    Optional<Usuario> findByEmail(String email);

    
}
