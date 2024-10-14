package com.csci318.microservice.user.Domain.Events;

import java.time.LocalDateTime;
import java.util.UUID;

import com.csci318.microservice.user.Constants.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderStatusChangedEvent {
    private UUID id;
    private String eventName;
    private UUID orderId;
    private UUID userId;
    private UUID restaurantId;
    private OrderStatus oldStatus;
    private OrderStatus status;
    private LocalDateTime changeTime;
    private Double totalPrice;
}
