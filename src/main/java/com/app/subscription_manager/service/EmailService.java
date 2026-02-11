package com.app.subscription_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.subscription_manager.model.Users;
import com.app.subscription_manager.model.Subscription;

import lombok.RequiredArgsConstructor;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@RequiredArgsConstructor
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;

    public void sendExpirationWarning(Users user, Subscription subscription) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@test.com");
        message.setTo(user.getEmail());
        message.setSubject("Aviso: Tu suscripción está por vencer");
        message.setText(String.format(
            "Hola %s,\n\n" +
            "Te recordamos que tu suscripción '%s' vencerá el %s.\n\n" +
            "Saludos.",
            user.getEmail(),
            subscription.getName(),
            subscription.getEndDate()
        ));
        
        mailSender.send(message);
    }

    public void sendRenewalWarning(Users user, Subscription subscription) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@test.com");
        message.setTo(user.getEmail());
        message.setSubject("Aviso: Tu suscripción se renovará pronto");
        message.setText(String.format(
            "Hola %s,\n\n" +
            "Te informamos que tu suscripción '%s' se renovará automáticamente el %s.\n\n" +
            "Saludos.",
            user.getEmail(),
            subscription.getName(),
            subscription.getEndDate()
        ));
        
        mailSender.send(message);
    }
}
