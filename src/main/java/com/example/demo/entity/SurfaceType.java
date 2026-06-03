package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "surface_types")
public class SurfaceType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int pricePerMinute;
}
