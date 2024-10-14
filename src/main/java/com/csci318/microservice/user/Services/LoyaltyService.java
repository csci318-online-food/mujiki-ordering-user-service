package com.csci318.microservice.user.Services;

import java.time.LocalDate;
import java.util.UUID;

import com.csci318.microservice.user.Constants.LoyaltyRank;
import com.csci318.microservice.user.DTOs.LoyaltyDTORequest;
import com.csci318.microservice.user.DTOs.LoyaltyDTOResponse;
import com.csci318.microservice.user.Domain.Entities.Loyalty;
import com.csci318.microservice.user.Domain.Relations.OrderStatusEvent;

public interface LoyaltyService {

     LoyaltyDTOResponse enrollInLoyalty(LoyaltyDTORequest loyaltyDTORequest);
     int getPointsByRank(LoyaltyRank rank);
     LocalDate calculateExpiryDate(Loyalty loyalty);
     void updateLoyaltyStatus(OrderStatusEvent event);
     LoyaltyDTOResponse getLoyaltyForUser(UUID userId);
}
