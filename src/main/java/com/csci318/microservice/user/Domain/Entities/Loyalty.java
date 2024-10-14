package com.csci318.microservice.user.Domain.Entities;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private UUID id;

     @Column(name = "loyalty_points")
     private int loyaltyPoints;

     @Column(name = "rank")
     private String rank;

     @Column(name = "point_expiry_date")
     private LocalDate pointExpiryDate;

     @Column(name = "user_id")
     private UUID userId;
}
