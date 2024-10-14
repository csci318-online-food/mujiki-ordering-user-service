package com.csci318.microservice.user.Domain.Services;

import com.csci318.microservice.user.Constants.LoyaltyRank;
import com.csci318.microservice.user.Domain.Entities.Loyalty;
import com.csci318.microservice.user.Domain.Entities.LoyaltyEntry;
import com.csci318.microservice.user.Domain.Relations.OrderStatusEvent;

import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class LoyaltyManager {
    public int getBonusPointsByRank(LoyaltyRank rank) {
        switch (rank) {
            case LoyaltyRank.PLATINUM: return 50;
            case LoyaltyRank.GOLD: return 35;
            case LoyaltyRank.SILVER: return 20;
            case LoyaltyRank.BRONZE: return 10;
            default: return 0; // Default
        }
    }

    public Duration getExpiryTime(Loyalty loyalty) {
        switch (loyalty.getRank()) {
            case LoyaltyRank.PLATINUM: return Duration.ofDays(365);
            case LoyaltyRank.GOLD: return Duration.ofDays(180);
            case LoyaltyRank.SILVER: return Duration.ofDays(90);
            case LoyaltyRank.BRONZE: return Duration.ofDays(30);
            default: return Duration.ZERO;
        }
    }

    public void refreshPoints(Loyalty loyalty, List<LoyaltyEntry> unexpiredEntries) {
        int totalPoints = unexpiredEntries.stream()
            .mapToInt(entry -> { return entry.getPointsLeft(); })
            .sum();
        loyalty.setPoints(totalPoints);

        int unexpiredPoints = unexpiredEntries.stream()
            .mapToInt(entry -> { return entry.getPointsEarned(); })
            .sum();

        if (unexpiredPoints >= 500) {
            loyalty.setRank(LoyaltyRank.PLATINUM);
        } else if (unexpiredPoints >= 350) {
            loyalty.setRank(LoyaltyRank.GOLD);
        } else if (unexpiredPoints >= 200) {
            loyalty.setRank(LoyaltyRank.SILVER);
        } else {
            loyalty.setRank(LoyaltyRank.BRONZE);
        }
    }

    public LoyaltyEntry addCompletedOrder(Loyalty loyalty, OrderStatusEvent event) {
        double orderTotal = event.getTotalPrice();
        LocalDateTime orderTime = event.getChangeTime();

        int pointsByRank = getBonusPointsByRank(loyalty.getRank());
        int points = (int)(orderTotal) + pointsByRank;

        LoyaltyEntry newEntry = new LoyaltyEntry();
        newEntry.setLoyaltyId(loyalty.getId());
        newEntry.setPointsEarned(points);
        newEntry.setPointsSpent(0);
        newEntry.setExpiryDate(orderTime.plus(getExpiryTime(loyalty)));

        return newEntry;
    }
}
