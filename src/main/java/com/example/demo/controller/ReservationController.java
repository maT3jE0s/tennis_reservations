package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ReservationRequest;
import com.example.demo.entity.Reservation;
import com.example.demo.service.ReservationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for managing court reservations.
 * Provides CRUD operations for courts
 * as well as filtering by court or customer phone number.
 */
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
    
    private final ReservationService reservationService;

    /**
     * Creates a new reservation.
     *
     * @param request reservation data
     * @return created reservation with calculated price
     */
    @PostMapping
    public ResponseEntity<Reservation> create(
            @Valid @RequestBody ReservationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(reservationService.create(request));
    }

    /**
     * Returns a reservation by its identifier.
     *
     * @param id reservation identifier
     * @return reservation details
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getById(id));
    }

    /**
     * Returns all reservations for a specific court number.
     *
     * @param courtNumber unique court number
     * @return list of reservations sorted by creation date
     */
    @GetMapping("/by-court/{courtNumber}")
    public ResponseEntity<List<Reservation>> getByCourtNumber(@PathVariable Integer courtNumber) {
        return ResponseEntity.ok(reservationService.getByCourtNumber(courtNumber));
    }

    /**
     * Returns reservations by customer phone number.
     *
     * @param phoneNumber customer phone number
     * @param futureOnly if true, returns only future reservations
     * @return list of reservations
     */
    @GetMapping("/by-phone/{phoneNumber}")
    public ResponseEntity<List<Reservation>> getByPhoneNumber(
            @PathVariable String phoneNumber,
            @RequestParam(defaultValue = "false") boolean futureOnly) {
        return ResponseEntity.ok(reservationService.getByPhoneNumber(phoneNumber, futureOnly));
    }

    /**
     * Updates an existing reservation.
     *
     * @param id reservation identifier
     * @param request updated reservation data
     * @return updated reservation
     */
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> update(
            @PathVariable Long id,
            @Valid @RequestBody ReservationRequest request) {
        return ResponseEntity.ok(reservationService.update(id, request));
    }

    /**
     * Soft deletes a reservation.
     *
     * @param id reservation identifier
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
