package com.AutoTrack.Networking.Controllers;

import com.AutoTrack.Networking.Configuration.JwtService;
import com.AutoTrack.Service.Interfaces.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private Services service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> login) {
        String username = login.get("username");
        String password = login.get("password");

        if(username == null || password == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var user = service.findUserByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(username + password ,user.get().getPasswordHash())) {
            String token = jwtService.generateToken(username);
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}

