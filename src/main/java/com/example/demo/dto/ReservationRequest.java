package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.entity.Reservation.GameType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request body for creating or updating a tennis court reservation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest {

    @NotNull(message = "Court number is required")
    private Integer courtNumber;

    @NotBlank(message = "User phone number is required")
    private String phoneNumber;

    @NotBlank(message = "user name is required")
    private String userName;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    @NotNull(message = "Game type is required")
    private GameType gameType;
}
