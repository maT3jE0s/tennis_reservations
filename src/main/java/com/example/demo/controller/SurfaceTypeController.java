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

@RestController
@RequestMapping("/api/surface-types")
@RequiredArgsConstructor
public class SurfaceTypeController {
    private final SurfaceTypeService surfaceTypeService;

    @PostMapping
    public ResponseEntity<SurfaceType> create(@Valid @RequestBody SurfaceTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(surfaceTypeService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<SurfaceType>> getAll() {
        return ResponseEntity.ok(surfaceTypeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurfaceType> getById(@PathVariable Long id) {
        return ResponseEntity.ok(surfaceTypeService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SurfaceType> update(@PathVariable Long id, @Valid @RequestBody SurfaceTypeRequest request) {
        return ResponseEntity.ok(surfaceTypeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        surfaceTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}