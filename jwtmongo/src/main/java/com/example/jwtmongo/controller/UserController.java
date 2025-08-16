package com.example.jwtmongo.controller;

import com.example.jwtmongo.model.User;
import com.example.jwtmongo.repository.UserRepository;
import com.example.jwtmongo.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserController(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user){
        userRepository.save(user);
        return ResponseEntity.ok("User Registered");
    }

    @PostMapping("/token")
    public ResponseEntity<String> generateToken(@RequestBody User user) {
        return userRepository.findByUsername(user.getUsername())
                .filter(u -> u.getPassword().equals(user.getPassword()))
                .map(u -> ResponseEntity.ok(jwtService.generateToken(u)))
                .orElse(ResponseEntity.status(400).body("Invalid credentials"));
    }

    @GetMapping("/protected")
    public ResponseEntity<String> protectedApi(){
        return ResponseEntity.ok("Access granted to protected API");
    }

}
