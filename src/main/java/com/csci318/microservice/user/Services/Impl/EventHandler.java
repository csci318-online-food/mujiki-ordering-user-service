package com.csci318.microservice.user.Services.Impl;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.csci318.microservice.user.Constants.OrderStatus;
import com.csci318.microservice.user.Domain.Relations.OrderStatusEvent;
import com.csci318.microservice.user.Services.LoyaltyService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventHandler {
    private final LoyaltyService loyaltyService;

    public EventHandler(LoyaltyService loyaltyService) {
        this.loyaltyService = loyaltyService;
    }

    @Bean
    public Consumer<OrderStatusEvent> handleOrderStatusEvent() {
        return event -> {
            log.info(
                "Received order status event for order " +
                event.getOrderId().toString() +
                " by user " + event.getUserId().toString() +
                ", status: " + event.getStatus().toString()
            );

            // Process orders COMPLETED
            if (event.getStatus() == OrderStatus.COMPLETED) {
                loyaltyService.updateLoyaltyStatus(event);
            }
        };
    }
}
