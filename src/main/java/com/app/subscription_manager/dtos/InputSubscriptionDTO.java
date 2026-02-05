package com.app.subscription_manager.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class InputSubscriptionDTO {

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
    private String periodicity;
    @NotNull
    private Boolean autoRenewal;
    @NotNull
    @Min(0)
    private Double price;

    public InputSubscriptionDTO(String id, String userId, String name, String description, String status,
            LocalDate startDate, String periodicity, Boolean autoRenewal, Double price) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.status = status;
        this.startDate = startDate;
        this.periodicity = periodicity;
        this.autoRenewal = autoRenewal;
        this.price = price;
    }

    

}
