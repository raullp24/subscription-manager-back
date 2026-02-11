package com.app.subscription_manager.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.app.subscription_manager.dtos.InputSubscriptionDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "subscriptions")
public class Subscription {
    @Id
    private String id;
    @NotNull
    private String userId;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private String status;
    @NotNull
    private LocalDate startDate;
    private LocalDate endDate;
    @NotNull
    private Periodicity periodicity;
    @NotNull
    private Boolean autoRenewal;
    @NotNull
    @Min(0)
    private Double price;

    public Subscription(InputSubscriptionDTO inputSubscriptionDTO) {
        this.userId = inputSubscriptionDTO.getUserId();
        this.name = inputSubscriptionDTO.getName();
        this.description = inputSubscriptionDTO.getDescription();
        this.status = inputSubscriptionDTO.getStatus();
        this.startDate = inputSubscriptionDTO.getStartDate();
        this.periodicity = Periodicity.valueOf(inputSubscriptionDTO.getPeriodicity());
        this.autoRenewal = inputSubscriptionDTO.getAutoRenewal();
        this.price = inputSubscriptionDTO.getPrice();
    }


}
