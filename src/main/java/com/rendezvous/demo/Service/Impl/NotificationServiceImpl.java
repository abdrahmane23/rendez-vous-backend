package com.rendezvous.demo.Service.Impl;


import com.rendezvous.demo.Exception.UserNotFoundException;
import com.rendezvous.demo.Service.NotificationService;
import com.rendezvous.demo.domain.Dtos.NotificationDto;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final Map<UUID, List<SseEmitter>> usersMap = new ConcurrentHashMap<>();

    @Override
    public void sendNotification(UUID userId, NotificationDto message) {
        List<SseEmitter> emitters = usersMap.get(userId);
        if (emitters != null) {
            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send(SseEmitter.event().name("notification").data(message));
                } catch (Exception e) {
                    emitters.remove(emitter);
                }
            }
        }else {
            throw new UserNotFoundException("User with ID " + userId + " not found or has no active connections.");
        }
    }

    @Override
    public SseEmitter SubscribeToNotifications(UUID userId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // Infinite timeout

        usersMap.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>()).add(emitter);
        emitter.onCompletion(() -> usersMap.get(userId).remove(emitter));
        emitter.onTimeout(() -> usersMap.get(userId).remove(emitter));
        return emitter;
    }
    @Override
    public void sendNotificationToAll(NotificationDto message) {

        usersMap.forEach((userId, emitters) -> {
            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send(
                            SseEmitter.event()
                                    .name("notification")
                                    .data(message)
                    );
                } catch (Exception e) {
                    emitters.remove(emitter);
                }
            }
        });
    }
}
