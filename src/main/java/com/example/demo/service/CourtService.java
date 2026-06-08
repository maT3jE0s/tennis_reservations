package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.CourtRequest;
import com.example.demo.entity.Court;
import com.example.demo.entity.SurfaceType;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourtRepository;
import com.example.demo.repository.SurfaceTypeRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service layer for managing tennis courts.
 * Handles business logic such as uniqueness validation
 * and surface type assignment.
 */
@Service
@RequiredArgsConstructor
public class CourtService {

    private final CourtRepository courtRepository;
    private final SurfaceTypeRepository surfaceTypeRepository;

    /**
     * Creates a new court with validation of uniqueness and surface type existence.
     *
     * @param request court data
     * @return created court
     */
    @Transactional
    public Court create(CourtRequest request) {
        if (courtRepository.findByCourtNumber(request.getCourtNumber()) != null)
            throw new IllegalArgumentException("Court with number " +request.getCourtNumber() + " already exists");

        SurfaceType surfaceType = requireSurfaceType(request.getSurfaceTypeId());

        Court court = Court.builder()
                .courtNumber(request.getCourtNumber())
                .surfaceType(surfaceType)
                .build();

        return courtRepository.save(court);
    }

    /**
     * Returns all courts.
     *
     * @return list of courts
     */
    @Transactional(readOnly = true)
    public List<Court> getAll() {
        return courtRepository.findAll();
    }

    /**
     * Retrieves court by ID.
     *
     * @param id court identifier
     * @return court entity
     */
    @Transactional(readOnly = true)
    public Court getById(Long id) {
        return requireCourt(id);
    }

    /**
     * Updates an existing court with validation of uniqueness.
     *
     * @param id court identifier
     * @param request updated court data
     * @return updated court
     */
    @Transactional
    public Court update(Long id, CourtRequest request) {
        Court court = requireCourt(id);

        SurfaceType surfaceType = requireSurfaceType(request.getSurfaceTypeId());
        
        Court toUpdate = courtRepository.findByCourtNumber(request.getCourtNumber());
        if (toUpdate != null && !toUpdate.getId().equals(id))
            throw new IllegalArgumentException("Court with number " + request.getCourtNumber() + " already exists");

        court.setCourtNumber(request.getCourtNumber());
        court.setSurfaceType(surfaceType);

        return courtRepository.save(court);
    }

    /**
     * Soft deletes a court.
     *
     * @param id court identifier
     */
    @Transactional
    public void delete(Long id) {
        requireCourt(id);
        courtRepository.delete(id);
    }

    private Court requireCourt(Long id) {
        Court court = courtRepository.findById(id);
        if (court == null)
            throw new ResourceNotFoundException("Court not found");
        
        return court;
    }

    private SurfaceType requireSurfaceType(Long id) {
        SurfaceType surfaceType = surfaceTypeRepository.findById(id);
        if (surfaceType == null)
            throw new ResourceNotFoundException("Surface type not found");

        return surfaceType;
    }
}
