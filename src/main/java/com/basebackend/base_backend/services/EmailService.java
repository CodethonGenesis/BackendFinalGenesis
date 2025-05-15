package com.basebackend.base_backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationCode(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Código de recuperación de contraseña");
        message.setText("Tu código de verificación es: " + code);
        message.setFrom("soporte.equipo.genesis@gmail.com");

        mailSender.send(message);
    }
}
