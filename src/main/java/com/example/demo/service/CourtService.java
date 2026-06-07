package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.CourtRequest;
import com.example.demo.entity.Court;
import com.example.demo.entity.SurfaceType;
import com.example.demo.repository.CourtRepository;
import com.example.demo.repository.SurfaceTypeRepository;

import lombok.RequiredArgsConstructor;

// TODO when id is used it is necessary to check whether the court with this id exists

@Service
@RequiredArgsConstructor
public class CourtService {
    private final CourtRepository courtRepository;
    private final SurfaceTypeRepository surfaceTypeRepository;

    @Transactional
    public Court create(CourtRequest request) {
        if (courtRepository.findByCourtNumber(request.getCourtNumber()) != null)
            throw new RuntimeException("Court with number " + request.getCourtNumber() + " already exists");

        SurfaceType surfaceType = surfaceTypeRepository.findById(request.getSurfaceTypeId());
        if (surfaceType == null)
            throw new RuntimeException("Surface type does not exist");

        Court court = Court.builder()
                .courtNumber(request.getCourtNumber())
                .surfaceType(surfaceType)
                .build();

        return courtRepository.save(court);
    }

    @Transactional(readOnly = true)
    public List<Court> getAll() {
        return courtRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Court getById(Long id) {
        return courtRepository.findById(id);
    }

    @Transactional
    public Court update(Long id, CourtRequest request) {
        Court court = courtRepository.findById(id);
        if (court == null)
            throw new RuntimeException("Court with id " + id + " does not exist");

        SurfaceType surfaceType = surfaceTypeRepository.findById(request.getSurfaceTypeId());
        if (surfaceType == null)
            throw new RuntimeException("Surface type does not exist");
        
        Court toUpdate = courtRepository.findByCourtNumber(request.getCourtNumber());
        if (toUpdate != null && !toUpdate.getId().equals(id))
            throw new RuntimeException("Court with number " + request.getCourtNumber() + " already exists");

        court.setCourtNumber(request.getCourtNumber());
        court.setSurfaceType(surfaceType);

        return courtRepository.save(court);
    }

    @Transactional
    public void delete(Long id) {
        courtRepository.delete(id);
    }
}
