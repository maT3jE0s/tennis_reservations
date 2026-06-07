package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.entity.Reservation.GameType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest {
    private Integer courtNumber;
    private String phoneNumber;
    private String userName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private GameType gameType;
}
