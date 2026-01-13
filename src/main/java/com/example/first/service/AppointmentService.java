package com.example.first.service;

import com.example.first.aspect.LogExecutionTime;
import com.example.first.dto.NotificationMessage;
import com.example.first.entity.Appointment;
import com.example.first.entity.User;
import com.example.first.enums.AppointmentStatus;
import com.example.first.repository.AppointmentRepository;
import com.example.first.repository.ServiceRepository;
import com.example.first.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final NotificationService notificationService;


    @Transactional
    @LogExecutionTime
    public Appointment createAppointment(Long customerId,
                                     Long serviceId, Appointment appointment)
{
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

      
        com.example.first.entity.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
User staff = service.getStaff();

        List<Appointment> conflicts = appointmentRepository.findConflictingAppointments(
                 staff,
                appointment.getAppointmentDate(),
                appointment.getStartTime(),
                appointment.getEndTime()
        );

        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Time slot is not available");
        }

        appointment.setCustomer(customer);
        appointment.setStaff(staff);
        appointment.setService(service);
        appointment.setStatus(AppointmentStatus.PENDING);

        Appointment saved = appointmentRepository.save(appointment);

      notificationService.sendToUser(
        customer.getId(),
        "Your appointment has been created with status: " + saved.getStatus()
);


        return saved;
    }

    @LogExecutionTime
    public List<Appointment> getCustomerAppointments(Long customerId) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return appointmentRepository.findByCustomer(customer);
    }

    @LogExecutionTime
    public List<Appointment> getStaffAppointments(Long staffId) {
        User staff = userRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        return appointmentRepository.findByStaff(staff);
    }

    @Transactional
    @LogExecutionTime
    public Appointment updateStatus(Long appointmentId, AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(status);
        Appointment updated = appointmentRepository.save(appointment);

        notificationService.sendToUser(
        appointment.getCustomer().getId(),
        "Your appointment status has been updated to: " + updated.getStatus()
);


        return updated;
    }

    @LogExecutionTime
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}
