package com.csci318.microservice.user.Services.Impl;

import com.csci318.microservice.user.Constants.Roles;
import com.csci318.microservice.user.DTOs.UserDTORequest;
import com.csci318.microservice.user.DTOs.UserDTOResponse;
import com.csci318.microservice.user.Entities.User;
import com.csci318.microservice.user.Exceptions.ServiceExceptionHandler.ErrorTypes;
import com.csci318.microservice.user.Exceptions.ServiceExceptionHandler.ServiceException;
import com.csci318.microservice.user.Mappers.Impl.UserMapperImpl;
import com.csci318.microservice.user.Repositories.UserRepository;
import com.csci318.microservice.user.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserMapperImpl userMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapperImpl userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDTOResponse signUp(UserDTORequest userDTORequest) {
        try {
            log.info("Creating user {}", userDTORequest);
            User user = this.userMapper.toEntities(userDTORequest);
            if (userRepository.existsByUsername(user.getUsername())) {
                log.error("Username already exists");
                throw new ServiceException(ErrorTypes.USER_ALREADY_EXIST.getMessage(), null, ErrorTypes.USER_USERNAME_ALREADY_EXIST);
            } else if (userRepository.existsByEmail(user.getEmail())) {
                log.error("Email already exists");
                throw new ServiceException(ErrorTypes.USER_ALREADY_EXIST.getMessage(), null, ErrorTypes.USER_EMAIL_ALREADY_EXIST);
            }
            user.setRole(Roles.USER);
            User savedUser = userRepository.save(user);
            log.info("Saved user successfully!");
            return userMapper.toDtos(savedUser);
        } catch (ServiceException e) {
            log.error("Service exception: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while creating user: ", e);
            throw new ServiceException(ErrorTypes.UNEXPECTED_ERROR.getMessage(), e, ErrorTypes.UNEXPECTED_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<UserDTOResponse> getAll() {
        try {
            return userRepository.findAll().stream()
                    .map(this.userMapper::toDtos)
                    .toList();
        } catch (Exception e) {
            log.error("Unexpected error occurred while retrieving all users: ", e);
            throw new ServiceException(ErrorTypes.UNEXPECTED_ERROR.getMessage(), e, ErrorTypes.UNEXPECTED_ERROR);
        }
    }
}
