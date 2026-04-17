package com.notification.service;

import com.notification.dto.DestinationRequestDTO;
import com.notification.dto.DestinationResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DestinationService {

    List<DestinationResponseDTO> findAll();

    DestinationResponseDTO findById(Long id);

    DestinationResponseDTO create(DestinationRequestDTO request);

    DestinationResponseDTO update(Long id, DestinationRequestDTO request);

    void delete(Long id);
}
