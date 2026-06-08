package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request body for creating or updating a court.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourtRequest {

    @NotNull(message = "Court number is required")
    private Integer courtNumber;

    @NotNull(message = "Surface type id is required")
    private Long surfaceTypeId;
}
