package com.example.first.controller;

import com.example.first.aspect.LogExecutionTime;
import com.example.first.entity.Service;
import com.example.first.entity.User;
import com.example.first.repository.ServiceRepository;
import com.example.first.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @LogExecutionTime
    public ResponseEntity<Service> createService(@RequestBody Service service) {
        if (service.getStaff() == null || service.getStaff().getId() == null) {
            throw new RuntimeException("يجب تحديد موظف للخدمة");
        }

        User staff = userRepository.findById(service.getStaff().getId())
                .orElseThrow(() -> new RuntimeException("الموظف غير موجود"));

        service.setStaff(staff);

        Service savedService = serviceRepository.save(service);

        if (savedService.getStaff() != null) {
            savedService.getStaff().getFirstName();
            savedService.getStaff().getLastName();
        }

        return ResponseEntity.ok(savedService);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @LogExecutionTime
    public ResponseEntity<List<Service>> getAllServices() {
        List<Service> services = serviceRepository.findByActiveTrue();

        for (Service service : services) {
            if (service.getStaff() != null) {
                service.getStaff().getFirstName();
                service.getStaff().getLastName();
                service.getStaff().getEmail();
                service.getStaff().getPhone();
            }
        }

        return ResponseEntity.ok(services);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @LogExecutionTime
    public ResponseEntity<?> getServiceById(@PathVariable Long id) {
        Optional<Service> serviceOptional = serviceRepository.findById(id);

        if (serviceOptional.isPresent()) {
            Service service = serviceOptional.get();

            if (service.getStaff() != null) {
                service.getStaff().getFirstName();
                service.getStaff().getLastName();
            }

            return ResponseEntity.ok(service);
        } else {
            String errorMessage = "الخدمة غير موجودة بالـ ID: " + id;
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorMessage);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @LogExecutionTime
    public ResponseEntity<?> deleteService(@PathVariable Long id) {
        if (!serviceRepository.existsById(id)) {
            String errorMessage = "الخدمة غير موجودة بالـ ID: " + id;
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorMessage);
        }

        serviceRepository.deleteById(id);

        String successMessage = "تم حذف الخدمة بنجاح (ID: " + id + ")";
        return ResponseEntity.ok(successMessage);
    }
}