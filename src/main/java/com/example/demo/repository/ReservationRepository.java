package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Reservation;

@Repository
@Transactional
public class ReservationRepository extends BaseRepository<Reservation> {

    public ReservationRepository() {
        super(Reservation.class);
    }

    @Transactional(readOnly = true)
    public List<Reservation> findByCourtNumber(Integer courtNumber) {
        return em.createQuery("SELECT r FROM Reservation r WHERE r.deleted = false AND r.court.courtNumber = :number AND r.court.deleted = false ORDER BY r.createdAt",
            Reservation.class)
            .setParameter("number", courtNumber)
            .getResultList();
    }

    @Transactional(readOnly = true)
    public List<Reservation> findByPhoneNumber(String phoneNumber, boolean futureOnly) {
        var query = em.createQuery("SELECT r FROM Reservation r WHERE r.deleted = false AND r.user.deleted = false AND r.user.phoneNumber = :number" + (futureOnly ? " AND r.startTime > :now" : ""), Reservation.class)
        .setParameter("number", phoneNumber);

        if (futureOnly)
            query.setParameter("now", LocalDateTime.now());

        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public boolean overlaps(Long courtId, LocalDateTime start, LocalDateTime end, Long excludeId) {
        var query = em.createQuery("SELECT COUNT(r) FROM Reservation r WHERE r.deleted = false AND r.court.id = :courtId AND :start < r.endTime AND :end > r.startTime" + (excludeId != null ? " AND r.id <> :except" : ""), Long.class)
            .setParameter("courtId", courtId)
            .setParameter("start", start)
            .setParameter("end", end);
        
        if (excludeId != null)
            query.setParameter("except", excludeId);

        return query.getSingleResult() > 0;
    }
}
