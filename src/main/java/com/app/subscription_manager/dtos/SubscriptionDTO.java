package com.app.subscription_manager.dtos;

import java.time.LocalDate;

import com.app.subscription_manager.model.Subscription;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {
    private String id;
    private String userId;
    private String name;
    private String description;
    private String status;
    private LocalDate startDate;
    private String periodicity;
    private Boolean autoRenewal;
    private Double price;
    private LocalDate endDate;

    public SubscriptionDTO(Subscription subscription) {
        this.id = subscription.getId();
        this.userId = subscription.getUserId();
        this.name = subscription.getName();
        this.description = subscription.getDescription();
        this.status = subscription.getStatus();
        this.startDate = subscription.getStartDate();
        this.periodicity = subscription.getPeriodicity().toString();
        this.autoRenewal = subscription.getAutoRenewal();
        this.price = subscription.getPrice();
        this.endDate = subscription.getEndDate();
    }

}
