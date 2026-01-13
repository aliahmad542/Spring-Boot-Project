package com.example.first.controller;

import com.example.first.enums.Role;
import com.example.first.entity.User;
import com.example.first.repository.UserRepository;
import com.example.first.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutController {

    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @PostMapping("/register-new")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("هذا الإيميل مستخدم مسبقًا");
        }

        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        if (user.getRole() == null) {
            user.setRole(Role.CUSTOMER);
        }

        User savedUser = userRepository.save(user);

        notificationService.sendToAll(
                "📢 مستخدم جديد سجل: " + savedUser.getFirstName() + " " + savedUser.getLastName()
        );

        savedUser.setPassword(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
