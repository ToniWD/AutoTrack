package com.AutoTrack.Networking.Controllers;

import com.AutoTrack.Domain.User;
import com.AutoTrack.Networking.Configuration.JwtService;
import com.AutoTrack.Service.Interfaces.Services;
import jakarta.servlet.annotation.HttpMethodConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class MainController {

    @Autowired
    private Services services;
    @Autowired
    private JwtService jwtService;

    @GetMapping("/hello")
    public ResponseEntity<?> hello()
    {
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7); // elimină "Bearer "

        String username = jwtService.extractUsername(token);
        Optional<User> me = services.findUserByUsername(username);
        if(me.isPresent()) {
            return ResponseEntity.ok(me.get());
        }
        else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found");
    }


}
