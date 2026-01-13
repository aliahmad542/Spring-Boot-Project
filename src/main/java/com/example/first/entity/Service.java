package com.example.first.entity;
import javax.persistence.*;
import lombok.Data;
import java.time.Duration;
import java.time.LocalDateTime;
@Entity
@Table(name = "services")
@Data

public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    private Double price;
    private Duration duration;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private User staff;
    
    private boolean active = true;
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
