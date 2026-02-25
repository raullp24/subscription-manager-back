package com.app.subscription_manager.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.app.subscription_manager.model.Periodicity;
import com.app.subscription_manager.model.Subscription;
import com.app.subscription_manager.model.Users;
import com.app.subscription_manager.repository.SubscriptionRepository;
import com.app.subscription_manager.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionNotificationService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 10 9 * * ?")
    public void checkSubscriptionsForNotification() {
        log.info("Verificando suscripciones para notificación...");

        LocalDate targetDate = LocalDate.now().plusDays(5); 
        List<Subscription> subscriptions = subscriptionRepository.findAll();

        for (Subscription subscription : subscriptions) {
            if (subscription.getEndDate() != null && subscription.getEndDate().equals(targetDate)) { 
                if (subscription.getAutoRenewal() && subscription.getPeriodicity() == Periodicity.MONTHLY) {
                    Users user = userRepository.findById(subscription.getUserId()).orElse(null);
                    emailService.sendRenewalWarning(user, subscription);
                    log.info("Enviado aviso de renovación para: {}", subscription.getName());
                } else if (!subscription.getAutoRenewal()) {
                    Users user = userRepository.findById(subscription.getUserId()).orElse(null);
                    emailService.sendExpirationWarning(user, subscription);
                    log.info("Enviado aviso de expiración para: {}", subscription.getName());
                }
            }
        }
    }

}
