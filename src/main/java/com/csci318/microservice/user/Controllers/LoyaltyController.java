package com.csci318.microservice.user.Controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csci318.microservice.user.Domain.Entities.Loyalty;
import com.csci318.microservice.user.Services.LoyaltyService;

@RestController
@RequestMapping("${api.endpoint.base-url}/loyalty")
public class LoyaltyController {

     private final LoyaltyService loyaltyService;

     public LoyaltyController(LoyaltyService loyaltyService) {
          this.loyaltyService = loyaltyService;
     }

    @PostMapping("/create/{userId}")
    public ResponseEntity<Loyalty> createLoyalty(@PathVariable("userId") UUID userId) {
        Loyalty loyalty = loyaltyService.createLoyalty(userId);
        return ResponseEntity.ok(loyalty);
    }

     
}
