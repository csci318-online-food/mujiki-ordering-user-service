package com.csci318.microservice.user.Services;

import java.util.UUID;

import com.csci318.microservice.user.DTOs.LoyaltyDTORequest;
import com.csci318.microservice.user.DTOs.LoyaltyDTOResponse;
import com.csci318.microservice.user.Domain.Relations.OrderStatusEvent;

public interface LoyaltyService {
     LoyaltyDTOResponse enrollInLoyalty(LoyaltyDTORequest loyaltyDTORequest);
     void updateLoyaltyStatus(OrderStatusEvent event);
     LoyaltyDTOResponse getLoyalty(UUID loyaltyId);
     LoyaltyDTOResponse getLoyaltyForUser(UUID userId);
}
