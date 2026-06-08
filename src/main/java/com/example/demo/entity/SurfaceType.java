package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a type of court surface.
 * Each surface type defines pricing rules for court usage.
 */
@Entity
@Table(name = "surface_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurfaceType extends BaseEntity {
    
    private String name;
    private double pricePerMinute;
}
