package com.issuetracking.app.controller;

import com.issuetracking.app.dto.ApiResponse;
import com.issuetracking.app.entity.User;
import com.issuetracking.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.createUser(user);
        ApiResponse<User> res = new ApiResponse<>(LocalDateTime.now(), 201, "User created", savedUser);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        ApiResponse<List<User>> res = new ApiResponse<>(LocalDateTime.now(), 200, "Success", users);
        return ResponseEntity.ok(res);
    }
}