package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.CourtRequest;
import com.example.demo.entity.Court;
import com.example.demo.service.CourtService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for managing tennis courts.
 * Provides CRUD operations for courts.
 */
@RestController
@RequestMapping("/api/courts")
@RequiredArgsConstructor
public class CourtController {
    
    private final CourtService courtService;

    /**
     * Creates a new court.
     *
     * @param request court data
     * @return created court
     */
    @PostMapping
    public ResponseEntity<Court> create(
            @Valid @RequestBody CourtRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(courtService.create(request));
    }

    /**
     * Returns all non-deleted courts.
     *
     * @return list of courts
     */
    @GetMapping
    public ResponseEntity<List<Court>> getAll() {
        return ResponseEntity.ok(courtService.getAll());
    }

    /**
     * Returns a court by its identifier.
     *
     * @param id court identifier
     * @return court details
     */
    @GetMapping("/{id}")
    public ResponseEntity<Court> getById(@PathVariable Long id) {
        return ResponseEntity.ok(courtService.getById(id));
    }

    /**
     * Updates an existing court.
     *
     * @param id court identifier
     * @param request updated court data
     * @return updated court
     */
    @PutMapping("/{id}")
    public ResponseEntity<Court> update(
            @PathVariable Long id,
            @Valid @RequestBody CourtRequest request) {
        return ResponseEntity.ok(courtService.update(id, request));
    }

    /**
     * Soft deletes a court.
     *
     * @param id court identifier
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courtService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
