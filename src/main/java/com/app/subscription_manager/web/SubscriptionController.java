package com.app.subscription_manager.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.subscription_manager.dtos.InputSubscriptionDTO;
import com.app.subscription_manager.dtos.SubscriptionDTO;
import com.app.subscription_manager.service.SubscriptionService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionDTO> createSubscription(@Valid @RequestBody InputSubscriptionDTO inputSubscriptionDTO){
        SubscriptionDTO subscriptionDTO = subscriptionService.create(inputSubscriptionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionDTO);
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptions() {
        List<SubscriptionDTO> subscriptions = subscriptionService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(subscriptions);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptionsByUserId(@PathVariable String userId) {
        List<SubscriptionDTO> subscriptions = subscriptionService.findByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(subscriptions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> getSubscriptionById(@PathVariable String id){
        SubscriptionDTO subscriptionDTO = subscriptionService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionDTO);
    }

    
    
    

}
