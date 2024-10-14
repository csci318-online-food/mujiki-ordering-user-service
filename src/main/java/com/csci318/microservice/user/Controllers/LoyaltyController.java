package com.csci318.microservice.user.Controllers;

import com.csci318.microservice.user.DTOs.LoyaltyDTORequest;
import com.csci318.microservice.user.DTOs.LoyaltyDTOResponse;
import com.csci318.microservice.user.Services.LoyaltyService;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.endpoint.base-url}/loyalty")
public class LoyaltyController {

     private final LoyaltyService loyaltyService;

     public LoyaltyController(LoyaltyService loyaltyService) {
          this.loyaltyService = loyaltyService;
     }

    @PostMapping("/enroll")
    public ResponseEntity<LoyaltyDTOResponse> enrollInLoyalty(
        @RequestBody LoyaltyDTORequest loyaltyDTORequest
    ) {
        LoyaltyDTOResponse loyaltyDTOResponse = loyaltyService.enrollInLoyalty(loyaltyDTORequest);
        return ResponseEntity.ok(loyaltyDTOResponse);
    }

    @GetMapping("/{loyaltyId}")
    public ResponseEntity<LoyaltyDTOResponse> getLoyaltyForUser(@PathVariable UUID loyaltyId) {
        LoyaltyDTOResponse loyaltyDTOResponse = loyaltyService.getLoyalty(loyaltyId);
        return ResponseEntity.ok(loyaltyDTOResponse);
    }
}
