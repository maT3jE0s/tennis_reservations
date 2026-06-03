package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Court court;

    @ManyToOne
    private User user;
    
    private LocalDateTime start;
    private LocalDateTime end;

    @Enumerated(EnumType.STRING)
    private GameType gameType;

    public static enum GameType{
        SINGLE, DOUBLE
    }
}
