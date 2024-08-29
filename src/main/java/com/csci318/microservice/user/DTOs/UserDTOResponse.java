package com.csci318.microservice.user.DTOs;

import com.csci318.microservice.user.Constants.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTOResponse {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Roles role;
}
