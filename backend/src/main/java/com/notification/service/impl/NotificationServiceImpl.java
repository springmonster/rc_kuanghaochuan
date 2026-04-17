package com.notification.service.impl;

import com.notification.dto.NotificationRequestDTO;
import com.notification.model.Destination;
import com.notification.model.NotificationLog;
import com.notification.repository.DestinationRepository;
import com.notification.repository.NotificationLogRepository;
import com.notification.service.NotificationService;
import com.notification.util.PlaceholderReplacer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private static final int MAX_RETRIES = 3;
    private static final long RETRY_INTERVAL_MS = 5000;

    private final DestinationRepository destinationRepository;
    private final NotificationLogRepository notificationLogRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void sendNotification(NotificationRequestDTO request) {
        Destination destination = destinationRepository.findById(request.getDestinationId())
                .orElseThrow(() -> new RuntimeException("Destination not found: " + request.getDestinationId()));

        NotificationLog logEntry = NotificationLog.builder()
                .destinationId(destination.getId())
                .destinationName(destination.getName())
                .status(NotificationLog.Status.PENDING)
                .retryCount(0)
                .requestPayload(toJson(request.getPayload()))
                .build();

        logEntry = notificationLogRepository.save(logEntry);

        boolean success = sendWithRetry(destination, request.getPayload(), logEntry);

        logEntry.setStatus(success ? NotificationLog.Status.SUCCESS : NotificationLog.Status.FAILED);
        notificationLogRepository.save(logEntry);
    }

    private boolean sendWithRetry(Destination destination, Map<String, String> payload, NotificationLog logEntry) {
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            logEntry.setRetryCount(attempt);

            try {
                boolean success = sendRequest(destination, payload);
                if (success) {
                    return true;
                }
            } catch (Exception e) {
                log.error("Attempt {} failed: {}", attempt, e.getMessage());
                logEntry.setErrorMessage(e.getMessage());
            }

            if (attempt < MAX_RETRIES) {
                try {
                    Thread.sleep(RETRY_INTERVAL_MS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        return false;
    }

    private boolean sendRequest(Destination destination, Map<String, String> payload) {
        String url = destination.getUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (destination.getApiKey() != null && !destination.getApiKey().isEmpty()) {
            headers.set("X-API-Key", destination.getApiKey());
        }

        if (destination.getHeaders() != null && !destination.getHeaders().isEmpty()) {
            String replacedHeaders = PlaceholderReplacer.replace(destination.getHeaders(), payload);
            for (String line : replacedHeaders.split("\n")) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    headers.set(parts[0].trim(), parts[1].trim());
                }
            }
        }

        Map<String, String> body = payload != null ? payload : new HashMap<>();
        String replacedBody;
        try {
            replacedBody = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            replacedBody = "{}";
        }

        if (destination.getHeaders() != null && destination.getHeaders().contains("{{")) {
            replacedBody = PlaceholderReplacer.replace(replacedBody, payload);
        }

        HttpEntity<String> entity = new HttpEntity<>(replacedBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        int statusCode = response.getStatusCode().value();
        return statusCode >= 200 && statusCode < 300;
    }

    private String toJson(Map<String, String> payload) {
        if (payload == null) {
            return "{}";
        }
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}
