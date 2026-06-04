package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Court court;

    @ManyToOne
    private User user;
    
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime creationTime;

    @Enumerated(EnumType.STRING)
    private GameType gameType;

    private boolean deleted = false;

    public static enum GameType{
        SINGLE, DOUBLE
    }
}
