package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Court;
import com.example.demo.entity.SurfaceType;
import com.example.demo.repository.CourtRepository;
import com.example.demo.repository.SurfaceTypeRepository;

import lombok.RequiredArgsConstructor;

@Component
@ConditionalOnProperty(name = "app.data-initialization", havingValue = "true")
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final SurfaceTypeRepository surfaceTypeRepository;
    private final CourtRepository courtRepository;

    @Override
    @Transactional
    public void run(String... args) {
        SurfaceType grass = surfaceTypeRepository.save(new SurfaceType("Grass", 0.5));
        SurfaceType clay = surfaceTypeRepository.save(new SurfaceType("Clay", 0.7));

        courtRepository.save(new Court(1, clay));
        courtRepository.save(new Court(2, clay));
        courtRepository.save(new Court(3, grass));
        courtRepository.save(new Court(4, grass));
    }
}
