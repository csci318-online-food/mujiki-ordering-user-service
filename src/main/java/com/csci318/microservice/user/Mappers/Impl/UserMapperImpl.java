package com.csci318.microservice.user.Mappers.Impl;

import com.csci318.microservice.user.DTOs.UserDTORequest;
import com.csci318.microservice.user.DTOs.UserDTOResponse;
import com.csci318.microservice.user.Entities.User;
import com.csci318.microservice.user.Mappers.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapperImpl implements Mapper<User, UserDTOResponse, UserDTORequest> {

    @Override
    public UserDTOResponse toDtos(User entity) {
        UserDTOResponse dto = new UserDTOResponse();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
        return dto;
    }

    @Override
    public User toEntities(UserDTORequest dto) {
        User entity = new User();
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setRole(dto.getRole());
        return entity;
    }

    @Override
    public List<UserDTOResponse> toDtos(List<User> users) {
        List<UserDTOResponse> dtos = new ArrayList<>();
        for (User user : users) {
            dtos.add(toDtos(user));
        }
        return dtos;
    }

    @Override
    public List<User> toEntities(List<UserDTORequest> dtos) {
        List<User> users = new ArrayList<>();
        for (UserDTORequest dto : dtos) {
            users.add(toEntities(dto));
        }
        return users;
    }
}
