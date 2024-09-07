package com.csci318.microservice.user.Services.Impl;

import com.csci318.microservice.user.Constants.Roles;
import com.csci318.microservice.user.DTOs.UserDTORequest;
import com.csci318.microservice.user.DTOs.UserDTOResponse;
import com.csci318.microservice.user.Entities.Relations.Address;
import com.csci318.microservice.user.Entities.User;
import com.csci318.microservice.user.Exceptions.ServiceExceptionHandler.ErrorTypes;
import com.csci318.microservice.user.Exceptions.ServiceExceptionHandler.ServiceException;
import com.csci318.microservice.user.Mappers.Impl.UserMapperImpl;
import com.csci318.microservice.user.Repositories.UserRepository;
import com.csci318.microservice.user.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;
    private final UserMapperImpl userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${address.url.service}")
    private String ADDRESS_URL;

    public UserServiceImpl(RestTemplate restTemplate, UserMapperImpl userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, ApplicationEventPublisher eventPublisher) {
        this.restTemplate = restTemplate;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
    }

    // Task B (1st): Create User
    @Transactional
    public UserDTOResponse signUp(UserDTORequest userDTORequest) {
        try {
            log.info("Creating user {}", userDTORequest);
            if (userDTORequest.getPassword() == null || userDTORequest.getPassword().isEmpty()) {
                log.error("Password cannot be empty");
                throw new ServiceException(ErrorTypes.USER_INVALID_INPUT.getMessage(), null, ErrorTypes.USER_INVALID_INPUT);
            }
            User user = this.userMapper.toEntities(userDTORequest);
            if (userRepository.existsByUsername(user.getUsername())) {
                log.error("Username already exists");
                throw new ServiceException(ErrorTypes.USER_ALREADY_EXIST.getMessage(), null, ErrorTypes.USER_USERNAME_ALREADY_EXIST);
            } else if (userRepository.existsByEmail(user.getEmail())) {
                log.error("Email already exists");
                throw new ServiceException(ErrorTypes.USER_ALREADY_EXIST.getMessage(), null, ErrorTypes.USER_EMAIL_ALREADY_EXIST);
            }
            // TODO: Control UUID generation.
            user.setId(UUID.randomUUID());
            user.setRole(Roles.USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            log.info("Saved user successfully!");
            eventPublisher.publishEvent(savedUser);
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

    @Transactional
    public UserDTOResponse findByUsername(String username) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ServiceException(ErrorTypes.USER_NOT_FOUND.getMessage(), null, ErrorTypes.USER_NOT_FOUND));
            return userMapper.toDtos(user);
        } catch (ServiceException e) {
            log.error("Service exception: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while retrieving user by username: ", e);
            throw new ServiceException(ErrorTypes.UNEXPECTED_ERROR.getMessage(), e, ErrorTypes.UNEXPECTED_ERROR);
        }
    }

    @Override
    public UserDTOResponse findById(UUID userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ServiceException(ErrorTypes.USER_NOT_FOUND.getMessage(), null, ErrorTypes.USER_NOT_FOUND));
            return userMapper.toDtos(user);
        } catch (ServiceException e) {
            log.error("Service exception: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while retrieving user by id: ", e);
            throw new ServiceException(ErrorTypes.UNEXPECTED_ERROR.getMessage(), e, ErrorTypes.UNEXPECTED_ERROR);
        }
    }

    @Override
    public List<Address> viewAddress(UUID userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ServiceException(ErrorTypes.USER_NOT_FOUND.getMessage(), null, ErrorTypes.USER_NOT_FOUND));
            return restTemplate.getForObject(ADDRESS_URL +"/forUser/"+ userId, List.class);
        } catch (ServiceException e) {
            log.error("Service exception: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while retrieving user by id: ", e);
            throw new ServiceException(ErrorTypes.UNEXPECTED_ERROR.getMessage(), e, ErrorTypes.UNEXPECTED_ERROR);
        }
    }
}
