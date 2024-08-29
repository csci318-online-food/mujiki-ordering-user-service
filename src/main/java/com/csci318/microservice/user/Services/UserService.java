package com.csci318.microservice.user.Services;

import com.csci318.microservice.user.DTOs.UserDTORequest;
import com.csci318.microservice.user.DTOs.UserDTOResponse;

import java.util.List;

public interface UserService {

    UserDTOResponse signUp(UserDTORequest userDTORequest);
    List<UserDTOResponse> getAll();
}
