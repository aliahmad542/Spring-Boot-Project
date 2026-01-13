package com.example.first.controller;

import com.example.first.aspect.LogExecutionTime;
import com.example.first.entity.User;
import com.example.first.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @LogExecutionTime
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @LogExecutionTime
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(user);
        } else {
            String errorMessage = "المستخدم غير موجود بالـ ID: " + id;
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorMessage);
        }
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @LogExecutionTime
    public ResponseEntity<?> getCurrentUserProfile() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("يجب تسجيل الدخول أولاً");
            }

            Object principal = authentication.getPrincipal();
            String userEmail;

            if (principal instanceof UserDetails) {
                userEmail = ((UserDetails) principal).getUsername();
            } else if (principal instanceof String) {
                userEmail = (String) principal;
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("بيانات المستخدم غير متاحة");
            }

            Optional<User> userOptional = userRepository.findByEmail(userEmail);

            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("المستخدم غير موجود: " + userEmail);
            }

            User user = userOptional.get();

            return ResponseEntity.ok(user);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("خطأ في الخادم: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @LogExecutionTime
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            String errorMessage = "المستخدم غير موجود بالـ ID: " + id;
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorMessage);
        }

        userRepository.deleteById(id);

        String successMessage = "تم حذف المستخدم بنجاح (ID: " + id + ")";
        return ResponseEntity.ok(successMessage);
    }
}