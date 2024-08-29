package com.csci318.microservice.user.DTOs;

import com.csci318.microservice.user.Constants.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTORequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Roles role;
}
