package com.csci318.microservice.user.Repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csci318.microservice.user.Domain.Entities.Loyalty;

@Repository
public interface LoyaltyRepository extends JpaRepository<Loyalty, UUID> {

     @Query("SELECT l FROM Loyalty l WHERE l.userId = :userId")
     Optional<Loyalty> findByUserId(UUID userId);
}


