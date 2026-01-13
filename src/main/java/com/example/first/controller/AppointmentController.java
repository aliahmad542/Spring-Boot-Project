package com.example.first.controller;

import com.example.first.entity.Appointment;
import com.example.first.enums.AppointmentStatus;
import com.example.first.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

@PostMapping
@PreAuthorize("hasRole('CUSTOMER')")
public ResponseEntity<Appointment> createAppointment(
        @RequestParam Long customerId,
        @RequestParam Long serviceId,
        @RequestBody Appointment appointment) {

    Appointment created = appointmentService.createAppointment(
            customerId, serviceId, appointment);
    return ResponseEntity.ok(created);
}


    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<List<Appointment>> getCustomerAppointments(
            @PathVariable Long customerId) {

        List<Appointment> appointments = appointmentService.getCustomerAppointments(customerId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/staff/{staffId}")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<List<Appointment>> getStaffAppointments(
            @PathVariable Long staffId) {

        List<Appointment> appointments = appointmentService.getStaffAppointments(staffId);
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Appointment> updateStatus(
            @PathVariable Long id,
            @RequestParam AppointmentStatus status) {

        Appointment updated = appointmentService.updateStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }
}
