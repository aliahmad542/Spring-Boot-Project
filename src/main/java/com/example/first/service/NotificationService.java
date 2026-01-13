package com.example.first.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendToUser(Long userId, String message) {
        messagingTemplate.convertAndSend("/topic/appointments/" + userId, message);// بحول الرسالة لصيغة stomp
    } // وبعدا بيستدعي بروكو افتراصضي

    public void sendToAll(String message) {
        messagingTemplate.convertAndSend("/topic/appointments/all", message);
    }
}
