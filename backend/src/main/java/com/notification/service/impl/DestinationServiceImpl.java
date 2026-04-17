package com.notification.service.impl;

import com.notification.dto.DestinationRequestDTO;
import com.notification.dto.DestinationResponseDTO;
import com.notification.model.Destination;
import com.notification.repository.DestinationRepository;
import com.notification.service.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;

    @Override
    public List<DestinationResponseDTO> findAll() {
        return destinationRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DestinationResponseDTO findById(Long id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found: " + id));
        return toDTO(destination);
    }

    @Override
    public DestinationResponseDTO create(DestinationRequestDTO request) {
        Destination destination = Destination.builder()
                .name(request.getName())
                .url(request.getUrl())
                .apiKey(request.getApiKey())
                .headers(request.getHeaders())
                .body(request.getBody())
                .retryCount(request.getRetryCount() != null ? request.getRetryCount() : 3)
                .build();
        Destination saved = destinationRepository.save(destination);
        return toDTO(saved);
    }

    @Override
    public DestinationResponseDTO update(Long id, DestinationRequestDTO request) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found: " + id));

        destination.setName(request.getName());
        destination.setUrl(request.getUrl());
        destination.setApiKey(request.getApiKey());
        destination.setHeaders(request.getHeaders());
        destination.setBody(request.getBody());
        if (request.getRetryCount() != null) {
            destination.setRetryCount(request.getRetryCount());
        }

        Destination updated = destinationRepository.save(destination);
        return toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if (!destinationRepository.existsById(id)) {
            throw new RuntimeException("Destination not found: " + id);
        }
        destinationRepository.deleteById(id);
    }

    private DestinationResponseDTO toDTO(Destination entity) {
        return DestinationResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .url(entity.getUrl())
                .apiKey(entity.getApiKey())
                .headers(entity.getHeaders())
                .body(entity.getBody())
                .retryCount(entity.getRetryCount())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
