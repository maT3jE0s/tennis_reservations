package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.example.demo.entity.SurfaceType;

@DataJpaTest
@Import(SurfaceTypeRepository.class)
public class SurfaceTypeRepositoryTest {

    @Autowired
    private SurfaceTypeRepository surfaceTypeRepository;

    private SurfaceType surfaceType;

    @BeforeEach
    void setup() {
        surfaceType = surfaceTypeRepository.save(new SurfaceType("Grass", 0.5));
    }

    @Test
    void testFindByName() {
        SurfaceType result = surfaceTypeRepository.findByName("Grass");
        assertNotNull(result);
        assertEquals(result, surfaceType);
    }

    @Test
    void testFindByName_notFound() {
        SurfaceType result = surfaceTypeRepository.findByName("Clay");
        assertNull(result);
    }

    @Test
    void testFindById() {
        SurfaceType result = surfaceTypeRepository.findById(surfaceType.getId());
        assertNotNull(result);
        assertEquals(surfaceType, result);
    }

    @Test
    void testFindById_notFound() {
        SurfaceType result = surfaceTypeRepository.findById(2L);
        assertNull(result);
    }

    @Test
    void testFindAll() {
        List<SurfaceType> result = surfaceTypeRepository.findAll();
        assertEquals(1, result.size());
        assertEquals(surfaceType, result.get(0));
    }

    @Test
    void testDelete() {
        surfaceTypeRepository.delete(surfaceType.getId());
        assertNull(surfaceTypeRepository.findById(surfaceType.getId()));
    }

    @Test
    void testDelete_notFound() {
        surfaceTypeRepository.delete(2L);
    }
}
