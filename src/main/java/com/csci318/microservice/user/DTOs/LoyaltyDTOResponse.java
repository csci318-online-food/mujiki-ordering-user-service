package com.csci318.microservice.user.DTOs;

import java.util.UUID;

import com.csci318.microservice.user.Constants.LoyaltyRank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoyaltyDTOResponse {
    private UUID id;
    private UUID userId;
    private int points;
    private LoyaltyRank rank;
}
