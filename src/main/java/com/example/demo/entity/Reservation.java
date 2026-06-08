package com.example.demo.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a tennis court reservation.
 * Contains booking time interval, customer, court and pricing information.
 * Supports single and double game types with different pricing rules.
 */
@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation extends BaseEntity {

    @ManyToOne
    private Court court;

    @ManyToOne
    private User user;
    
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @CreationTimestamp
    private LocalDateTime createdAt;
    
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private GameType gameType;

    /**
     * Indicates whether a reservation is for a singles or doubles match.
     * Doubles reservations incur a 1.5× price multiplier.
     */
    public static enum GameType{
        SINGLE, DOUBLE
    }
}
