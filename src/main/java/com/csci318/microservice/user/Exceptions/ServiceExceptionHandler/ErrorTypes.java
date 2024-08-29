package com.csci318.microservice.user.Exceptions.ServiceExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorTypes {

    // General Errors for Service Layer
    UNEXPECTED_ERROR("UNEXPECTED_ERROR", "An unexpected error occurred!"),

    //USER_ERROR
    USER_NOT_FOUND("USER_NOT_FOUND", "Can not find the user!"),
    USER_ALREADY_EXIST("USER_ALREADY_EXIST", "User already exist!"),
    USER_EMAIL_ALREADY_EXIST("USER_EMAIL_ALREADY_EXISTS", "Email already exists!"),
    USER_USERNAME_ALREADY_EXIST("USERNAME_ALREADY_EXISTS", "Username already exists!"),
    USER_INVALID_INPUT("USER_INVALID_INPUT", "Invalid input!"),;

    private final String code;
    private final String message;


}
