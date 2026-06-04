package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.example.demo.entity.Court;

import java.util.List;

@Repository
public class CourtRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Court findById(Long id) {
        return entityManager.find(Court.class, id);
    }

    public List<Court> findAll() {
        return entityManager.createQuery("SELECT c FROM Court c where c.deleted = false",
            Court.class).getResultList();
    }

    public void save(Court court) {
        if (court.getId() == null) {
            entityManager.persist(court);
        } else {
            entityManager.merge(court);
        }
    }
}
