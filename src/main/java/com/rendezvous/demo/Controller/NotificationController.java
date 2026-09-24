package com.rendezvous.demo.Controller;


import com.rendezvous.demo.Service.NotificationService;
import com.rendezvous.demo.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@RestController
@RequestMapping("api/notifications")
@RequiredArgsConstructor

public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/subscribe")
    public SseEmitter subscribeToNotifications(@AuthenticationPrincipal Jwt jwt) {
        System.out.println("Subscribing to notifications for user: " + jwt.getSubject());
        UUID userId  = Util.parseUserId(jwt);
        SseEmitter emitter= notificationService.SubscribeToNotifications(userId);
        return emitter;
    }
}
