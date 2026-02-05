package com.app.subscription_manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.subscription_manager.dtos.InputSubscriptionDTO;
import com.app.subscription_manager.dtos.SubscriptionDTO;
import com.app.subscription_manager.exception.UserNotFoundException;
import com.app.subscription_manager.model.Subscription;
import com.app.subscription_manager.repository.SubscriptionRepository;
import com.app.subscription_manager.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    public SubscriptionDTO create(@Valid InputSubscriptionDTO inputSubscriptionDTO){
        Subscription subscription = new Subscription(inputSubscriptionDTO);
        return new SubscriptionDTO(subscriptionRepository.save(subscription));
    }

    public List<SubscriptionDTO> findAll(){
        return subscriptionRepository.findAll().stream().map(SubscriptionDTO::new).toList();
    }

    public List<SubscriptionDTO> findByUserId(String userId) throws UserNotFoundException {
        if(userRepository.findById(userId).isEmpty()){
            throw new UserNotFoundException(userId);
        }
        return subscriptionRepository.findByUserId(userId).stream().map(SubscriptionDTO::new).toList();
    }
}
