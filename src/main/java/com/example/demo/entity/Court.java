package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "courts")
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Integer courtNumber;

    @ManyToOne
    private SurfaceType surfaceType;
}
