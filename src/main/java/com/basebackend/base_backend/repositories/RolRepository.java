package com.basebackend.base_backend.repositories;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basebackend.base_backend.entities.Rol;

@Repository
public interface  RolRepository  extends JpaRepository<Rol, Long>{

    Optional<Rol> findByName(String name);

}
