package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Court;

/**
 * Repository for managing Court entities.
 * Extends BaseRepository and provides additional query methods.
 */
@Repository
@Transactional
public class CourtRepository extends BaseRepository<Court> {
    
    public CourtRepository() {
        super(Court.class);
    }

    /**
     * Finds a court by its unique court number.
     * Only returns non-deleted courts.
     *
     * @param courtNumber unique court number
     * @return court or null if not found
     */
    @Transactional(readOnly = true)
    public Court findByCourtNumber(Integer courtNumber) {
        List<Court> results = em.createQuery(
                "SELECT c FROM Court c " +
                "WHERE c.courtNumber = :number " +
                "AND c.deleted = false",
                Court.class)
            .setParameter("number", courtNumber)
            .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
