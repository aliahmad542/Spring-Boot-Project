package com.example.first.AI.dto;

import lombok.Data;
import java.util.List;

@Data
public class GeminiRequestDTO {

    private String workingHours;
    private List<String> bookedAppointments; 
    private String serviceName;
    private int serviceDurationMinutes; 
    private String requestedTime;
}
