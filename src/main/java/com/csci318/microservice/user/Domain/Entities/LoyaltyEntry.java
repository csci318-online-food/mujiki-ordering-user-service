package com.csci318.microservice.user.Domain.Entities;

import com.csci318.microservice.user.Utils.Annotations.OneToMany;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "loyalty_entries")
public class LoyaltyEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "loyalty_id")
    @OneToMany(targetEntity = Loyalty.class)
    private UUID loyaltyId;

    @Column(name = "points_earned")
    private int pointsEarned;

    @Column(name = "points_spent")
    private int pointsSpent;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    public int getPointsLeft() {
        return pointsEarned - pointsSpent;
    }
}
