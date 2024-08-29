package com.csci318.microservice.user.Services;

import com.csci318.microservice.user.DTOs.UserDTORequest;
import com.csci318.microservice.user.DTOs.UserDTOResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDTOResponse signUp(UserDTORequest userDTORequest);
    List<UserDTOResponse> getAll();
    UserDTOResponse findByUsername(String username);
    UserDTOResponse findById(UUID userId);
}
