package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.example.demo.entity.Court;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.SurfaceType;
import com.example.demo.entity.User;
import com.example.demo.entity.Reservation.GameType;

@DataJpaTest
@Import({ReservationRepository.class, CourtRepository.class, UserRepository.class, SurfaceTypeRepository.class})
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CourtRepository courtRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SurfaceTypeRepository surfaceTypeRepository;

    private Reservation reservation;
    private Court court;
    private User user;
    private SurfaceType surfaceType;

    private final LocalDateTime START = LocalDateTime.of(2026, 1, 1, 10, 0);
    private final LocalDateTime END   = LocalDateTime.of(2026, 1, 1, 11, 0);


    @BeforeEach
    void setup() {
        user = userRepository.save(new User("0908123456", "Jan"));
        surfaceType = surfaceTypeRepository.save(new SurfaceType("Grass", 0.5));
        court = courtRepository.save(new Court(1, surfaceType));
        reservation = reservationRepository.save(Reservation.builder()
            .court(court)
            .user(user)
            .startTime(START)
            .endTime(END)
            .gameType(GameType.SINGLE)
            .totalPrice(30)
            .build()
        );
    }

    @Test
    void testFindByCourtNumber() {
        List<Reservation> result = reservationRepository.findByCourtNumber(1);
        assertEquals(1, result.size());
        assertEquals(reservation, result.get(0));
    }

    @Test
    void testFindByPhoneNumber() {
        List<Reservation> result = reservationRepository.findByPhoneNumber("0908123456", false);
        assertEquals(1, result.size());
        assertEquals(reservation, result.get(0));
    }

    @Test
    void testFindByPhoneNumber_futureOnly() {
        List<Reservation> result = reservationRepository.findByPhoneNumber("0908123456", true);
        assertTrue(result.isEmpty());
    }

    @Test
    void testOverlaps() {
        assertTrue(reservationRepository.overlaps(court.getId(), START, END, null));
    }

    @Test
    void testOverlaps_excludesId() {
        assertFalse(reservationRepository.overlaps(court.getId(), START, END, reservation.getId()));
    }
}
