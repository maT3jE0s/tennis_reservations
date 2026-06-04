package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Court;
import com.example.demo.repository.CourtRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CourtService {
    private final CourtRepository repository;

    public CourtService(CourtRepository repository) {
        this.repository = repository;
    }

    public List<Court> getAll() {
        return repository.findAll();
    }

    public Court getById(Long id) {
        return repository.findById(id);
    }

    public Court save(Court court) {
        repository.save(court);
        return court;
    }

    public void delete(Long id) {
        Court court = getById(id);
        court.setDeleted(true);
        save(court);
    }
}
