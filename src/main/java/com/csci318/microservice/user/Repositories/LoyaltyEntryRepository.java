package com.csci318.microservice.user.Repositories;

import com.csci318.microservice.user.Domain.Entities.LoyaltyEntry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface LoyaltyEntryRepository extends JpaRepository<LoyaltyEntry, UUID> {
    @Query("SELECT le FROM LoyaltyEntry le WHERE le.loyaltyId = :loyaltyId AND le.expiryDate > :expiry")
    List<LoyaltyEntry> findByLoyaltyIdExpiresAfterDate(UUID loyaltyId, LocalDateTime expiry);
}
