package com.notification.controller;

import com.notification.dto.NotificationRequestDTO;
import com.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Void> sendNotification(@Valid @RequestBody NotificationRequestDTO request) {
        notificationService.sendNotification(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
