package com.csci318.microservice.user.Services;

import java.time.LocalDate;
import java.util.UUID;

import com.csci318.microservice.user.Domain.Entities.Loyalty;

public interface LoyaltyService {
     
     Loyalty createLoyalty(UUID userId);
     int calculatePoints(double orderTotal, Loyalty loyalty);
     int getPointsByRank(String rank);
     LocalDate calculateExpiryDate(Loyalty loyalty);
     void updateLoyaltyPoints(UUID uuid, int points);
     Loyalty findLoyaltyByUserId(UUID userId);
}
