package com.basebackend.base_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import com.basebackend.base_backend.entities.VerificationCode;

public interface VerificationCodeService {

    List<VerificationCode> findAll();

    Page<VerificationCode> findAll(Pageable pageable);

    Optional<VerificationCode> findById(@NonNull Long id);

    Optional<VerificationCode> findByEmail(String email);

    void deleteById(Long id);
    
    // 🔹 Añadir estos:
    VerificationCode generateCode(String email);
    
    Optional<VerificationCode> findValidCode(String email, String code);
    
    void markCodeAsUsed(VerificationCode code);

    VerificationCode createCode(VerificationCode code); 

}
