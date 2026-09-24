package com.rendezvous.demo.Service;

import com.rendezvous.demo.domain.Dtos.NotificationDto;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

public interface NotificationService {

    void sendNotification(UUID userId, NotificationDto message);
    SseEmitter SubscribeToNotifications(UUID userId);
    void sendNotificationToAll(NotificationDto message);
}
