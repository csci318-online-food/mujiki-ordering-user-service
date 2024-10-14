package com.csci318.microservice.user.Services.Impl;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.csci318.microservice.user.Constants.LoyaltyRank;
import com.csci318.microservice.user.DTOs.LoyaltyDTORequest;
import com.csci318.microservice.user.DTOs.LoyaltyDTOResponse;
import com.csci318.microservice.user.Domain.Entities.Loyalty;
import com.csci318.microservice.user.Domain.Relations.OrderStatusEvent;
import com.csci318.microservice.user.Mappers.Impl.LoyaltyMapper;
import com.csci318.microservice.user.Repositories.LoyaltyRepository;
import com.csci318.microservice.user.Services.LoyaltyService;

@Service
public class LoyaltyServiceImpl implements LoyaltyService {

    private final LoyaltyRepository loyaltyRepository;
    private final LoyaltyMapper loyaltyMapper;

    public LoyaltyServiceImpl(
        LoyaltyRepository loyaltyRepository,
        LoyaltyMapper loyaltyMapper
    ) {
        this.loyaltyRepository = loyaltyRepository;
        this.loyaltyMapper = loyaltyMapper;
    }

    @Override
    public LoyaltyDTOResponse enrollInLoyalty(LoyaltyDTORequest loyaltyDTORequest) {
        Loyalty loyalty = new Loyalty();
        loyalty.setId(UUID.randomUUID());
        loyalty.setPoints(0);
        loyalty.setRank(LoyaltyRank.BRONZE);
        loyalty.setUserId(loyaltyDTORequest.getUserId());
        loyaltyRepository.save(loyalty);
        return loyaltyMapper.toDtos(loyalty);
    }

    @Override
    public int getPointsByRank(LoyaltyRank rank) {
        switch (rank) {
            case LoyaltyRank.PLATINUM: return 50;
            case LoyaltyRank.GOLD: return 35;
            case LoyaltyRank.SILVER: return 20;
            case LoyaltyRank.BRONZE: return 10;
            default: return 0; // Default
        }
    }


    @Override
    public LocalDate calculateExpiryDate(Loyalty loyalty) {
        switch (loyalty.getRank()) {
            case LoyaltyRank.GOLD: return LocalDate.now().plusYears(1);
            case LoyaltyRank.SILVER: return LocalDate.now().plusMonths(6);
            case LoyaltyRank.BRONZE: return LocalDate.now().plusMonths(3);
            default: return LocalDate.now().plusMonths(1);
        }
    }

    @Override
    public void updateLoyaltyStatus(OrderStatusEvent event) {
        UUID userId = event.getUserId();
        double orderTotal = event.getTotalPrice();

        Loyalty loyalty = loyaltyRepository.findByUserId(userId).orElseThrow();

        int pointsByRank = getPointsByRank(loyalty.getRank());
        int points = (int)(orderTotal / 10) + pointsByRank;

        loyalty.setPoints(loyalty.getPoints() + points);
        loyaltyRepository.save(loyalty);
    }

    @Override
    public LoyaltyDTOResponse getLoyaltyForUser(UUID userId) {
        return loyaltyMapper.toDtos(loyaltyRepository.findByUserId(userId).orElseThrow());
    }

}

