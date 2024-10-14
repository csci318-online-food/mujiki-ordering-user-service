package com.csci318.microservice.user.Domain.Entities;

import java.util.UUID;

import com.csci318.microservice.user.Constants.LoyaltyRank;
import com.csci318.microservice.user.Utils.Annotations.OneToOne;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "loyalty")
public class Loyalty {
     @Id
     @GeneratedValue(strategy = GenerationType.UUID)
     private UUID id;

     @Column(name = "user_id")
     @OneToOne(targetEntity = User.class)
     private UUID userId;

     @Column(name = "points")
     private int points;

     @Column(name = "rank")
     @Enumerated(EnumType.STRING)
     private LoyaltyRank rank;
}
