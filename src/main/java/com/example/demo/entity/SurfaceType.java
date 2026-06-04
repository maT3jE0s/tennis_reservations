package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "surface_types")
public class SurfaceType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int pricePerMinute;
    private boolean deleted = false;
}
