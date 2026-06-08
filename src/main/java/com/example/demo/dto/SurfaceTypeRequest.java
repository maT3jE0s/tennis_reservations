package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request body for creating or updating a surface type.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurfaceTypeRequest {

    @NotBlank(message = "Surface name is required")
    private String name;

    @NotNull(message = "Price per minute is required")
    private double pricePerMinute;
}
