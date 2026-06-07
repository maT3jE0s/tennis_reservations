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

import lombok.RequiredArgsConstructor;;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(reservationService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getById(id));
    }

    @GetMapping("/by-court/{courtNumber}")
    public ResponseEntity<List<Reservation>> getByCourtNumber(@PathVariable Integer courtNumber) {
        return ResponseEntity.ok(reservationService.getByCourtNumber(courtNumber));
    }

    @GetMapping("/by-phone/{phoneNumber}")
    public ResponseEntity<List<Reservation>> getByPhoneNumber(@PathVariable String phoneNumber, @RequestParam(defaultValue = "false") boolean futureOnly) {
        return ResponseEntity.ok(reservationService.getByPhoneNumber(phoneNumber, futureOnly));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> update(@PathVariable Long id, @RequestBody ReservationRequest request) {
        return ResponseEntity.ok(reservationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
