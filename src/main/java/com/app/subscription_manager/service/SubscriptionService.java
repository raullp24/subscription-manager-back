package com.app.subscription_manager.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.subscription_manager.dtos.InputSubscriptionDTO;
import com.app.subscription_manager.dtos.SubscriptionDTO;
import com.app.subscription_manager.exception.SubscriptionNotFoundException;
import com.app.subscription_manager.exception.UserNotFoundException;
import com.app.subscription_manager.model.Periodicity;
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
        if(!inputSubscriptionDTO.getAutoRenewal()){
            if(inputSubscriptionDTO.getPeriodicity().equals(Periodicity.MONTHLY.name())){
                subscription.setEndDate(subscription.getStartDate().plusMonths(1));
            } else if(inputSubscriptionDTO.getPeriodicity().equals(Periodicity.YEARLY.name())){
                subscription.setEndDate(subscription.getStartDate().plusYears(1));
            }
        }
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

    public SubscriptionDTO findById(String id) throws SubscriptionNotFoundException {
        return subscriptionRepository.findById(id).map(SubscriptionDTO::new).orElseThrow(() -> new SubscriptionNotFoundException(id));
    }

    public SubscriptionDTO update(String id, @Valid InputSubscriptionDTO inputSubscriptionDTO) throws SubscriptionNotFoundException {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(() -> new SubscriptionNotFoundException(id));
        subscription.setName(inputSubscriptionDTO.getName());
        subscription.setPrice(inputSubscriptionDTO.getPrice());
        subscription.setDescription(inputSubscriptionDTO.getDescription());
        subscription.setAutoRenewal(inputSubscriptionDTO.getAutoRenewal());
        subscription.setPeriodicity(Periodicity.valueOf(inputSubscriptionDTO.getPeriodicity()));
        subscription.setEndDate(inputSubscriptionDTO.getEndDate());
        subscription.setPrice(inputSubscriptionDTO.getPrice());
        return new SubscriptionDTO(subscriptionRepository.save(subscription));
    }

    public SubscriptionDTO cancel(String id) throws SubscriptionNotFoundException {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(() -> new SubscriptionNotFoundException(id));
        subscription.setStatus("cancelled");
        LocalDate currentDate = LocalDate.now();
        LocalDate nextRenewal = subscription.getStartDate();
        if(subscription.getAutoRenewal()){
            if(subscription.getPeriodicity().equals(Periodicity.MONTHLY)){
                while(!nextRenewal.isAfter(currentDate)){
                    nextRenewal = nextRenewal.plusMonths(1);
                }
                subscription.setEndDate(nextRenewal);
            } else if(subscription.getPeriodicity().equals(Periodicity.YEARLY)){
                while(!nextRenewal.isAfter(currentDate)){
                    nextRenewal = nextRenewal.plusYears(1);
                }
                subscription.setEndDate(nextRenewal);
            }
            subscription.setEndDate(nextRenewal);
            subscription.setAutoRenewal(false);
        }
        return new SubscriptionDTO(subscriptionRepository.save(subscription));
    }
}
