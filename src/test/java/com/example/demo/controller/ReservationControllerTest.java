package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.dto.ReservationRequest;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.Reservation.GameType;
import com.example.demo.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    private Reservation reservation;

    private final LocalDateTime START = LocalDateTime.of(2026, 1, 1, 10, 0);
    private final LocalDateTime END   = LocalDateTime.of(2026, 1, 1, 11, 0);

    @BeforeEach
    void setup() {
        reservation = Reservation.builder()
            .startTime(START)
            .endTime(END)
            .gameType(GameType.SINGLE)
            .totalPrice(30)
            .build();
        
        reservation.setId(1L);
    }

    @Test
    void testCreate() throws Exception {
        ReservationRequest request = ReservationRequest.builder()
            .courtNumber(1)
            .startTime(START)
            .endTime(END)
            .gameType(GameType.SINGLE)
            .phoneNumber("0908123456")
            .userName("Jan")
            .build();

        when(reservationService.create(any())).thenReturn(reservation);

        mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.totalPrice").value(30));
    }

    @Test
    void testGetByCourtNumber() throws Exception {
        when(reservationService.getByCourtNumber(1)).thenReturn(List.of(reservation));

        mockMvc.perform(get("/api/reservations/by-court/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].totalPrice").value(30));
    }

    @Test
    void testGetById() throws Exception {
        when(reservationService.getById(1L)).thenReturn(reservation);

        mockMvc.perform(get("/api/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.totalPrice").value(30));
    }

    @Test
    void testGetByPhoneNumber() throws Exception {
        when(reservationService.getByPhoneNumber("0908123456", false)).thenReturn(List.of(reservation));

        mockMvc.perform(get("/api/reservations/by-phone/0908123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].totalPrice").value(30));
    }

    @Test
    void testUpdate() throws Exception {
        ReservationRequest request = ReservationRequest.builder()
            .courtNumber(1)
            .startTime(START)
            .endTime(END)
            .gameType(GameType.SINGLE)
            .phoneNumber("0908123456")
            .userName("Jan")
            .build();

        when(reservationService.update(eq(1L), any())).thenReturn(reservation);

        mockMvc.perform(put("/api/reservations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.totalPrice").value(30));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(reservationService).delete(1L);
        mockMvc.perform(delete("/api/reservations/1"))
                .andExpect(status().isNoContent());
    }
}
