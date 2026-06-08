package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.SurfaceTypeRequest;
import com.example.demo.entity.SurfaceType;
import com.example.demo.service.SurfaceTypeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for managing surface types of tennis courts.
 * Surface types define pricing rules for court usage.
 */
@RestController
@RequestMapping("/api/surface-types")
@RequiredArgsConstructor
public class SurfaceTypeController {
    
    private final SurfaceTypeService surfaceTypeService;

    /**
     * Creates a new surface type.
     *
     * @param request surface type data (name, price per minute, etc.)
     * @return created surface type
     */
    @PostMapping
    public ResponseEntity<SurfaceType> create(
            @Valid @RequestBody SurfaceTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(surfaceTypeService.create(request));
    }

    /**
     * Returns all surface types.
     *
     * @return list of surface types
     */
    @GetMapping
    public ResponseEntity<List<SurfaceType>> getAll() {
        return ResponseEntity.ok(surfaceTypeService.getAll());
    }

    /**
     * Returns a surface type by its ID.
     *
     * @param id surface type identifier
     * @return surface type details
     */
    @GetMapping("/{id}")
    public ResponseEntity<SurfaceType> getById(@PathVariable Long id) {
        return ResponseEntity.ok(surfaceTypeService.getById(id));
    }

    /**
     * Updates an existing surface type.
     *
     * @param id surface type identifier
     * @param request updated surface type data
     * @return updated surface type
     */
    @PutMapping("/{id}")
    public ResponseEntity<SurfaceType> update(
            @PathVariable Long id,
            @Valid @RequestBody SurfaceTypeRequest request) {
        return ResponseEntity.ok(surfaceTypeService.update(id, request));
    }

    /**
     * Soft deletes a surface type.
     *
     * @param id surface type identifier
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        surfaceTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}