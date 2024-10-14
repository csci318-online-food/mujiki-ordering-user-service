package com.csci318.microservice.user.Mappers.Impl;

import com.csci318.microservice.user.DTOs.LoyaltyDTORequest;
import com.csci318.microservice.user.DTOs.LoyaltyDTOResponse;
import com.csci318.microservice.user.Domain.Entities.Loyalty;
import com.csci318.microservice.user.Mappers.Mapper;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoyaltyMapper implements Mapper<Loyalty, LoyaltyDTOResponse, LoyaltyDTORequest> {

    @Override
    public LoyaltyDTOResponse toDtos(Loyalty entity) {
        LoyaltyDTOResponse dto = new LoyaltyDTOResponse();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setPoints(entity.getPoints());
        dto.setRank(entity.getRank());
        return dto;
    }

    @Override
    public Loyalty toEntities(LoyaltyDTORequest dto) {
        Loyalty entity = new Loyalty();
        // entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        // entity.setPoints(dto.getPoints());
        // entity.setRank(dto.getRank());
        return entity;
    }

    @Override
    public List<LoyaltyDTOResponse> toDtos(List<Loyalty> loyaltyList) {
        List<LoyaltyDTOResponse> dtos = new ArrayList<>();
        for (Loyalty loyalty : loyaltyList) {
            dtos.add(toDtos(loyalty));
        }
        return dtos;
    }

    @Override
    public List<Loyalty> toEntities(List<LoyaltyDTORequest> dtos) {
        List<Loyalty> loyaltyList = new ArrayList<>();
        for (LoyaltyDTORequest dto : dtos) {
            loyaltyList.add(toEntities(dto));
        }
        return loyaltyList;
    }
}
