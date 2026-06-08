package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Reservation;

/**
 * Repository for managing Reservation entities.
 * Provides custom queries for filtering by court, user and time intervals,
 * including overlap detection for reservation validation.
 */
@Repository
@Transactional
public class ReservationRepository extends BaseRepository<Reservation> {

    public ReservationRepository() {
        super(Reservation.class);
    }

    /**
     * Finds all reservations for a given court number.
     * Results are ordered by creation time.
     *
     * @param courtNumber unique court number
     * @return list of reservations for the court
     */
    @Transactional(readOnly = true)
    public List<Reservation> findByCourtNumber(Integer courtNumber) {
        return em.createQuery(
                "SELECT r FROM Reservation r " +
                "WHERE r.deleted = false " +
                "AND r.court.courtNumber = :number " +
                "AND r.court.deleted = false " +
                "ORDER BY r.createdAt",
                Reservation.class)
            .setParameter("number", courtNumber)
            .getResultList();
    }

    /**
     * Finds reservations by customer phone number.
     * Optionally filters only future reservations.
     *
     * @param phoneNumber customer phone number
     * @param futureOnly if true, returns only upcoming reservations
     * @return list of reservations
     */
    @Transactional(readOnly = true)
    public List<Reservation> findByPhoneNumber(String phoneNumber, boolean futureOnly) {
        var query = em.createQuery(
            "SELECT r FROM Reservation r " +
            "WHERE r.deleted = false " +
            "AND r.user.deleted = false " +
            "AND r.user.phoneNumber = :number" +
            (futureOnly ? " AND r.startTime > :now" : ""),
            Reservation.class)
        .setParameter("number", phoneNumber);

        if (futureOnly)
            query.setParameter("now", LocalDateTime.now());

        return query.getResultList();
    }

    /**
     * Checks if a time interval overlaps with existing reservations for a court.
     *
     * @param courtId court identifier
     * @param start start time of new reservation
     * @param end end time of new reservation
     * @param excludeId reservation ID to exclude (used when updating)
     * @return true if overlap exists, otherwise false
     */
    @Transactional(readOnly = true)
    public boolean overlaps(Long courtId, LocalDateTime start, LocalDateTime end, Long excludeId) {
        var query = em.createQuery(
            "SELECT COUNT(r) FROM Reservation r " +
            "WHERE r.deleted = false " +
            "AND r.court.id = :courtId " +
            "AND :start < r.endTime " +
            "AND :end > r.startTime" +
            (excludeId != null ? " AND r.id <> :except" : ""),
            Long.class)
        .setParameter("courtId", courtId)
        .setParameter("start", start)
        .setParameter("end", end);
        
        if (excludeId != null)
            query.setParameter("except", excludeId);

        return query.getSingleResult() > 0;
    }
}
