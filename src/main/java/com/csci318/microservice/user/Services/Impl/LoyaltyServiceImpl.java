package com.csci318.microservice.user.Services.Impl;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.csci318.microservice.user.Domain.Entities.Loyalty;
import com.csci318.microservice.user.Repositories.LoyaltyRepository;
import com.csci318.microservice.user.Services.LoyaltyService;

@Service
public class LoyaltyServiceImpl implements LoyaltyService {

    private final LoyaltyRepository loyaltyRepository;

    public LoyaltyServiceImpl(LoyaltyRepository loyaltyRepository) {
        this.loyaltyRepository = loyaltyRepository;
    }

    @Override
    public Loyalty createLoyalty(UUID userId) {
        Loyalty loyalty = new Loyalty();
        loyalty.setLoyaltyPoints(0);
        loyalty.setRank("Bronze");
        loyalty.setPointExpiryDate(LocalDate.now().plusMonths(1));
        loyalty.setUserId(userId);
        return loyaltyRepository.save(loyalty);
    }

    @Override
    public int calculatePoints(double orderTotal, Loyalty loyalty) {
        int pointsByRank = this.getPointsByRank(loyalty.getRank());
        
        int points = (int) (orderTotal / 10) + pointsByRank;
        
        return points;
    }

    @Override
    public int getPointsByRank(String rank) {
        switch (rank) {
            case "Platinum": return 50;
            case "Gold": return 35;
            case "Silver": return 20;
            case "Bronze": return 10;
            default: return 0; // Default
        }
    }
    

    @Override
    public LocalDate calculateExpiryDate(Loyalty loyalty) {
        switch (loyalty.getRank()) {
            case "Gold": return LocalDate.now().plusYears(1);
            case "Silver": return LocalDate.now().plusMonths(6);
            case "Bronze": return LocalDate.now().plusMonths(3);
            default: return LocalDate.now().plusMonths(1);
        }
    }

    @Override
    public void updateLoyaltyPoints(UUID userId, int points) {
        Loyalty loyalty = loyaltyRepository.findByUserId(userId).orElseThrow();
        loyalty.setLoyaltyPoints(loyalty.getLoyaltyPoints() + points);
        loyalty.setPointExpiryDate(calculateExpiryDate(loyalty));
        loyaltyRepository.save(loyalty);
    }

    public Loyalty findLoyaltyByUserId(UUID userId) {
        return loyaltyRepository.findByUserId(userId).orElseThrow();
    }

}

