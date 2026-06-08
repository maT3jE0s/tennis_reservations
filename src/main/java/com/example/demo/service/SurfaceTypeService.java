package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.SurfaceTypeRequest;
import com.example.demo.entity.SurfaceType;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.SurfaceTypeRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service layer for managing surface types.
 * Surface types represent a pricing catalog used for court reservation pricing.
 */
@Service
@RequiredArgsConstructor
public class SurfaceTypeService {

    private final SurfaceTypeRepository surfaceTypeRepository;

    /**
     * Creates a new surface type with uniqueness validation.
     *
     * @param request surface type data
     * @return created surface type
     */
    @Transactional
    public SurfaceType create(SurfaceTypeRequest request) {
        if (surfaceTypeRepository.findByName(request.getName()) != null)
            throw new IllegalArgumentException("Surface type " + request.getName() + " already exists");

        SurfaceType surfaceType = SurfaceType.builder()
                .name(request.getName())
                .pricePerMinute(request.getPricePerMinute())
                .build();

        return surfaceTypeRepository.save(surfaceType);
    }

    /**
     * Returns all surface types.
     *
     * @return list of surface types
     */
    @Transactional(readOnly = true)
    public List<SurfaceType> getAll() {
        return surfaceTypeRepository.findAll();
    }

    /**
     * Retrieves surface type by ID.
     *
     * @param id surface type identifier
     * @return surface type entity
     */
    @Transactional(readOnly = true)
    public SurfaceType getById(Long id) {
        return requireSurfaceType(id);
    }

    /**
     * Updates existing surface type with uniqueness validation.
     *
     * @param id surface type identifier
     * @param request updated data
     * @return updated surface type
     */
    @Transactional
    public SurfaceType update(Long id, SurfaceTypeRequest request) {
        SurfaceType surfaceType = requireSurfaceType(id);

        SurfaceType toUpdate = surfaceTypeRepository.findByName(request.getName());

        if (toUpdate != null && !toUpdate.getId().equals(id))
            throw new IllegalArgumentException("Surface type " + request.getName() + " already exists");

        surfaceType.setName(request.getName());
        surfaceType.setPricePerMinute(request.getPricePerMinute());
        return surfaceTypeRepository.save(surfaceType);
    }

    /**
     * Soft deletes a surface type.
     *
     * @param id surface type identifier
     */
    @Transactional
    public void delete(Long id) {
        requireSurfaceType(id);
        surfaceTypeRepository.delete(id);
    }

    private SurfaceType requireSurfaceType(Long id) {
        SurfaceType surfaceType = surfaceTypeRepository.findById(id);
        if (surfaceType == null)
            throw new ResourceNotFoundException("Surface type not found");

        return surfaceType;
    }
}
