package com.basebackend.base_backend.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basebackend.base_backend.entities.VerificationCode;

import jakarta.validation.constraints.NotNull;

@Repository
public interface  VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    
    @Override
    @NotNull
    Page<VerificationCode> findAll(@NotNull Pageable pageable);

    @Override
    Optional<VerificationCode> findById(Long id);

    Optional<VerificationCode> findByEmail(String email);

    Optional<VerificationCode> findByEmailAndCodeAndUsedFalseAndExpirationAfter(String email, String code, LocalDateTime now);
}
