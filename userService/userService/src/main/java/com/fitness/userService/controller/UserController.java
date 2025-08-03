package com.fitness.userService.controller;

import com.fitness.userService.dto.RegisterRequest;
import com.fitness.userService.dto.UserResponse;
import com.fitness.userService.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserByProfile(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserProfile(userId)) ;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@Valid  @RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(userService.register(registerRequest)) ;
    }
}
