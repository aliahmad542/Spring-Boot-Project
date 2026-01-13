package com.example.first.AI.util;

import com.example.first.AI.dto.GeminiRequestDTO;

public class GeminiPromptBuilder {

    public static String build(GeminiRequestDTO req) {
        String booked = String.join(", ", req.getBookedAppointments());

        return """
        You are an appointment scheduling assistant.

        Working hours: %s
        Booked appointments: %s
        Service: %s
        Duration: %d minutes
        Requested time: %s

        Return ONLY JSON:
        {
          "bestTime": "",
          "alternativeTime": ""
        }
        """.formatted(
                req.getWorkingHours(),
                booked,
                req.getServiceName(),
                req.getServiceDurationMinutes(),
                req.getRequestedTime()
        );
    }
}
