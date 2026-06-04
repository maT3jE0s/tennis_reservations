package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Court;
import com.example.demo.service.CourtService;

@RestController
@RequestMapping("/api/courts")
public class CourtController {
    private final CourtService service;

    public CourtController(CourtService service) {
        this.service = service;
    }

    @GetMapping
    public List<Court> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Court getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Court create(@RequestBody Court court) {
        return service.save(court);
    }

    @PutMapping("/{id}")
    public Court update(@PathVariable Long id, @RequestBody Court court) {
        court.setId(id);
        return service.save(court);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
