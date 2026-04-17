package com.notification.service;

import com.notification.dto.NotificationRequestDTO;

public interface NotificationService {

    void sendNotification(NotificationRequestDTO request);
}
