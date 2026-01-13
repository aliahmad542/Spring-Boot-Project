package com.example.first.repository;
import com.example.first.entity.Appointment;
import com.example.first.entity.User;
import com.example.first.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByCustomer(User customer);
    List<Appointment> findByStaff(User staff);
    List<Appointment> findByStatus(AppointmentStatus status);
    @Query("SELECT a FROM Appointment a WHERE a.staff = :staff " +
           "AND a.appointmentDate = :date " +
           "AND ((a.startTime <= :endTime AND a.endTime >= :startTime))")
   
    List<Appointment> findConflictingAppointments(
            @Param("staff") User staff,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);
}
