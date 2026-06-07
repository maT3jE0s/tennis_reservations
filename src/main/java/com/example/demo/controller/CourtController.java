package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.CourtRequest;
import com.example.demo.entity.Court;
import com.example.demo.service.CourtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/courts")
@RequiredArgsConstructor
public class CourtController {
    private final CourtService courtService;

    @PostMapping
    public ResponseEntity<Court> create(@RequestBody CourtRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(courtService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<Court>> getAll() {
        return ResponseEntity.ok(courtService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Court> getById(@PathVariable Long id) {
        return ResponseEntity.ok(courtService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Court> update(@PathVariable Long id, @RequestBody CourtRequest request) {
        return ResponseEntity.ok(courtService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courtService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
