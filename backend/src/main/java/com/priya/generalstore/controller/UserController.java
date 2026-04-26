package com.priya.generalstore.controller;

import com.priya.generalstore.model.User;
import com.priya.generalstore.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import com.priya.generalstore.dto.LoginRequest;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://13.218.245.88:5173")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        User existingUser = userRepository.findByEmailAndPassword(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        if (existingUser != null) {
            return "Login successful";
        } else {
            return "Invalid email or password";
        }
    }
}