package com.csci318.microservice.user.Controllers;

import com.csci318.microservice.user.DTOs.UserDTORequest;
import com.csci318.microservice.user.DTOs.UserDTOResponse;
import com.csci318.microservice.user.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/user")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTOResponse>> findAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTOResponse> createUser(@RequestBody UserDTORequest userDTORequest) {
        UserDTOResponse userDTOResponse = userService.signUp(userDTORequest);
        return ResponseEntity.ok(userDTOResponse);
    }

    @GetMapping("/{username}")
    public UserDTOResponse getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }


}
