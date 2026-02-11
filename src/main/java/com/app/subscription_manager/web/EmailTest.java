package com.app.subscription_manager.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.subscription_manager.exception.UserNotFoundException;
import com.app.subscription_manager.service.SubscriptionNotificationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/email")
@Slf4j
public class EmailTest {
    
    @Autowired
    private SubscriptionNotificationService subscriptionNotificationService;

    @GetMapping("/send-email")
    public ResponseEntity<String> testNotifications() {
        try {
            subscriptionNotificationService.checkSubscriptionsForNotification();
            return ResponseEntity.ok("Verificación de notificaciones ejecutada correctamente. Revisa los logs y tu inbox de Mailtrap.");
        } catch (UserNotFoundException e) {
            log.error("Error al verificar notificaciones: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error inesperado: " + e.getMessage());
        }
    }
}
