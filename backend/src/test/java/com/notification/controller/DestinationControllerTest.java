package com.notification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.dto.DestinationRequestDTO;
import com.notification.dto.DestinationResponseDTO;
import com.notification.service.DestinationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DestinationController.class)
class DestinationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DestinationService destinationService;

    @Test
    void shouldReturnAllDestinations() throws Exception {
        DestinationResponseDTO dto = DestinationResponseDTO.builder()
                .id(1L)
                .name("Test Destination")
                .url("https://api.example.com")
                .apiKey("test-key")
                .retryCount(3)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(destinationService.findAll()).thenReturn(Arrays.asList(dto));

        mockMvc.perform(get("/api/v1/destinations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Destination"));
    }

    @Test
    void shouldReturnDestinationById() throws Exception {
        DestinationResponseDTO dto = DestinationResponseDTO.builder()
                .id(1L)
                .name("Test Destination")
                .url("https://api.example.com")
                .apiKey("test-key")
                .retryCount(3)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(destinationService.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/destinations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Destination"));
    }

    @Test
    void shouldCreateDestination() throws Exception {
        DestinationRequestDTO request = DestinationRequestDTO.builder()
                .name("New Destination")
                .url("https://api.example.com")
                .apiKey("new-key")
                .retryCount(3)
                .build();

        DestinationResponseDTO response = DestinationResponseDTO.builder()
                .id(1L)
                .name("New Destination")
                .url("https://api.example.com")
                .apiKey("new-key")
                .retryCount(3)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(destinationService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/destinations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Destination"));
    }

    @Test
    void shouldRejectCreateWhenNameIsBlank() throws Exception {
        DestinationRequestDTO request = DestinationRequestDTO.builder()
                .name("")
                .url("https://api.example.com")
                .build();

        mockMvc.perform(post("/api/v1/destinations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateDestination() throws Exception {
        DestinationRequestDTO request = DestinationRequestDTO.builder()
                .name("Updated Destination")
                .url("https://api.new-example.com")
                .apiKey("updated-key")
                .retryCount(5)
                .build();

        DestinationResponseDTO response = DestinationResponseDTO.builder()
                .id(1L)
                .name("Updated Destination")
                .url("https://api.new-example.com")
                .apiKey("updated-key")
                .retryCount(5)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(destinationService.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/v1/destinations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Destination"))
                .andExpect(jsonPath("$.retryCount").value(5));
    }

    @Test
    void shouldDeleteDestination() throws Exception {
        mockMvc.perform(delete("/api/v1/destinations/1"))
                .andExpect(status().isNoContent());
    }
}
