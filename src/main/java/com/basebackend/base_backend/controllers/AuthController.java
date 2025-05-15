package com.basebackend.base_backend.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basebackend.base_backend.entities.VerificationCode;
import com.basebackend.base_backend.repositories.UsuarioRepository;
import com.basebackend.base_backend.services.EmailService;
import com.basebackend.base_backend.services.VerificationCodeService;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private VerificationCodeService codeService;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-code")
    public ResponseEntity<?> sendCode(@RequestBody EmailRequest req) {
        var user = userRepository.findByEmail(req.email());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("El email no existe");
        }

        VerificationCode vc = codeService.generateCode(req.email());

        // Enviar por email de verdad
        emailService.sendVerificationCode(
                req.email(),
                vc.getCode()
        );

        return ResponseEntity.ok("Código enviado correctamente");
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@RequestBody VerifyCodeRequest req) {
        var codeOpt = codeService.findValidCode(req.email(), req.code());
        if (codeOpt.isEmpty()) {
            return ResponseEntity.ok().body(Map.of(
                    "success", false,
                    "message", "Código inválido o expirado"
            ));
        }
        return ResponseEntity.ok().body(Map.of(
                "success", true,
                "message", "Código correcto"
        ));
    }

    @PostMapping("/reset-password-with-code")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest req) {
        var codeOpt = codeService.findValidCode(req.email(), req.code());
        if (codeOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Código inválido o expirado");
        }

        var userOpt = userRepository.findByEmail(req.email());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        var user = userOpt.get();

        // Verificar si la nueva contraseña es igual a la anterior
        if (BCrypt.checkpw(req.newPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("La nueva contraseña no puede ser igual a la anterior");
        }

        // Actualizar la contraseña
        user.setPassword(BCrypt.hashpw(req.newPassword(), BCrypt.gensalt()));
        userRepository.save(user);

        codeService.markCodeAsUsed(codeOpt.get());
        return ResponseEntity.ok("Contraseña actualizada con éxito");
    }
    

    record EmailRequest(String email) {

    }

    record VerifyCodeRequest(String email, String code) {

    }

    record ResetPasswordRequest(String email, String code, String newPassword) {

    }

}
