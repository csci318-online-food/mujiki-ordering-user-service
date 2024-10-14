package com.csci318.microservice.user.Services.Impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.csci318.microservice.user.Constants.LoyaltyRank;
import com.csci318.microservice.user.DTOs.LoyaltyDTORequest;
import com.csci318.microservice.user.DTOs.LoyaltyDTOResponse;
import com.csci318.microservice.user.DTOs.UserDTOResponse;
import com.csci318.microservice.user.Domain.Entities.Loyalty;
import com.csci318.microservice.user.Domain.Entities.LoyaltyEntry;
import com.csci318.microservice.user.Domain.Relations.OrderStatusEvent;
import com.csci318.microservice.user.Domain.Services.LoyaltyManager;
import com.csci318.microservice.user.Mappers.Impl.LoyaltyMapper;
import com.csci318.microservice.user.Repositories.LoyaltyEntryRepository;
import com.csci318.microservice.user.Repositories.LoyaltyRepository;
import com.csci318.microservice.user.Services.LoyaltyService;
import com.csci318.microservice.user.Services.UserService;

import jakarta.transaction.Transactional;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class LoyaltyServiceImpl implements LoyaltyService {

    private final LoyaltyRepository loyaltyRepository;
    private final LoyaltyEntryRepository loyaltyEntryRepository;
    private final LoyaltyMapper loyaltyMapper;
    private final UserService userService;
    private final LoyaltyManager loyaltyManager;

    public LoyaltyServiceImpl(
        LoyaltyRepository loyaltyRepository,
        LoyaltyEntryRepository loyaltyEntryRepository,
        UserService userService,
        LoyaltyMapper loyaltyMapper
    ) {
        this.loyaltyRepository = loyaltyRepository;
        this.loyaltyEntryRepository = loyaltyEntryRepository;
        this.userService = userService;
        this.loyaltyMapper = loyaltyMapper;

        this.loyaltyManager = new LoyaltyManager();
    }

    @Transactional
    @Override
    public LoyaltyDTOResponse enrollInLoyalty(LoyaltyDTORequest loyaltyDTORequest) {
        UserDTOResponse user = userService.findById(loyaltyDTORequest.getUserId());
        if (user == null) {
            throw new RuntimeException("Invalid user ID.");
        }

        Loyalty loyalty = new Loyalty();
        loyalty.setId(UUID.randomUUID());
        loyalty.setRank(LoyaltyRank.BRONZE);
        loyalty.setUserId(loyaltyDTORequest.getUserId());
        loyaltyRepository.save(loyalty);

        return loyaltyMapper.toDtos(loyalty);
    }

    @Transactional
    @Override
    public void updateLoyaltyStatus(OrderStatusEvent event) {
        List<LoyaltyEntry> unexpiredEntries = new ArrayList<>();

        Loyalty loyalty = getLoyaltyAndRefreshPoints(
            event.getUserId(),
            null,
            unexpiredEntries
        );

        if (loyalty == null) {
            return;
        }

        LoyaltyEntry newEntry = loyaltyManager.addCompletedOrder(loyalty, event);
        loyaltyEntryRepository.save(newEntry);

        if (newEntry.getExpiryDate().isAfter(LocalDateTime.now())) {
            unexpiredEntries.add(newEntry);
            loyaltyManager.refreshPoints(loyalty, unexpiredEntries);
        }

        loyaltyRepository.save(loyalty);
    }

    @Transactional
    @Override
    public LoyaltyDTOResponse getLoyalty(UUID loyaltyId) {
        Loyalty refreshedLoyalty = getLoyaltyAndRefreshPoints(
            null,
            loyaltyId,
            null
        );

        if (refreshedLoyalty == null) {
            throw new RuntimeException("Cannot find specified loyalty object.");
        }

        loyaltyRepository.save(refreshedLoyalty);

        return loyaltyMapper.toDtos(refreshedLoyalty);
    }

    @Transactional
    @Override
    public LoyaltyDTOResponse getLoyaltyForUser(UUID userId) {
        Loyalty refreshedLoyalty = getLoyaltyAndRefreshPoints(
            userId,
            null,
            null
        );

        if (refreshedLoyalty == null) {
            throw new RuntimeException("Cannot find loyalty object for specified user.");
        }

        loyaltyRepository.save(refreshedLoyalty);

        return loyaltyMapper.toDtos(refreshedLoyalty);
    }

    private Loyalty getLoyaltyAndRefreshPoints(
        UUID userId,
        UUID loyaltyId,
        @Nullable List<LoyaltyEntry> unexpiredEntries
    ) {
        Loyalty loyalty = null;

        if (userId != null) {
            loyalty = loyaltyRepository.findByUserId(userId).orElse(null);
        } else if (loyaltyId != null) {
            loyalty = loyaltyRepository.findById(loyaltyId).orElse(null);
        }

        if (loyalty == null) {
            return loyalty;
        }

        List<LoyaltyEntry> entries = loyaltyEntryRepository.findByLoyaltyIdExpiresAfterDate(
            loyalty.getId(),
            LocalDateTime.now()
        );
        loyaltyManager.refreshPoints(loyalty, entries);

        if (unexpiredEntries != null) {
            unexpiredEntries.addAll(entries);
        }

        return loyalty;
    }
}
