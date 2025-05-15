package com.basebackend.base_backend.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basebackend.base_backend.entities.VerificationCode;
import com.basebackend.base_backend.repositories.VerificationCodeRepository;
import com.basebackend.base_backend.services.VerificationCodeService;

@Service("verificationCodeService")
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final VerificationCodeRepository repository;

    
    public VerificationCodeServiceImpl(VerificationCodeRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VerificationCode> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VerificationCode> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VerificationCode> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<VerificationCode> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public VerificationCode createCode(VerificationCode code) {
        return repository.save(code);
    }

    public VerificationCode generateCode(String email) {
        String code = String.format("%06d", new Random().nextInt(999999));
        VerificationCode vc = new VerificationCode();
        vc.setEmail(email);
        vc.setCode(code);
        vc.setExpiration(LocalDateTime.now().plusMinutes(10));
        vc.setUsed(false);
        return repository.save(vc);
    }

    public Optional<VerificationCode> findValidCode(String email, String code) {
        return repository.findByEmailAndCodeAndUsedFalseAndExpirationAfter(email, code, LocalDateTime.now());
    }

    public void markCodeAsUsed(VerificationCode code) {
        code.setUsed(true);
        repository.save(code);
    }
}
