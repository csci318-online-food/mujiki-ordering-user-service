package com.csci318.microservice.user.Services.Impl;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;

import com.csci318.microservice.user.Constants.OrderStatus;
import com.csci318.microservice.user.Domain.Entities.Loyalty;
import com.csci318.microservice.user.Domain.Events.OrderStatusChangedEvent;
import com.csci318.microservice.user.Services.LoyaltyService;

public class EventHandler {
     
     private final LoyaltyService loyaltyService;

     public EventHandler(LoyaltyService loyaltyService) {
          this.loyaltyService = loyaltyService;
     }

    @Bean
    public Consumer<OrderStatusChangedEvent> handleOrderStatusChangedEvent() {
        return event -> {
            // Process orders COMPLETED
            if (event.getStatus() == OrderStatus.COMPLETED) {
                Loyalty loyalty = loyaltyService.findLoyaltyByUserId(event.getUserId());
                int points = loyaltyService.calculatePoints(event.getTotalPrice(), loyalty);
                loyaltyService.updateLoyaltyPoints(event.getUserId(), points);
            }
        };
    }
}
