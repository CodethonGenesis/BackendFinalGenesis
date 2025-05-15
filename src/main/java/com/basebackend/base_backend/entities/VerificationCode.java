package com.basebackend.base_backend.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;

@Entity
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vercode_id_seq")
    @SequenceGenerator(name = "vercode_id_seq", sequenceName = "vercode_id_seq", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(nullable = false) 
    @Email
    private String email;

    @Column(nullable = false)
    private String code;
    
    @Column(nullable = false)
    private LocalDateTime expiration;
    
    @Column(nullable = false)
    private Boolean used;


    public VerificationCode() {
    }

    
    public VerificationCode(@Email String email, String code, LocalDateTime expiration, Boolean used) {
        this.email = email;
        this.code = code;
        this.expiration = expiration;
        this.used = used;
    }


    public String getEmail() {
        return email;
    }
    public void setEmail(@Email String email) {
        this.email = email;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public LocalDateTime getExpiration() {
        return expiration;
    }
    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }
    public Boolean getUsed() {
        return used;
    }
    public void setUsed(Boolean used) {
        this.used = used;
    }
    public Long getId() {
        return id;
    }
}
