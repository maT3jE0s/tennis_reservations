package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.SurfaceType;


@Repository
@Transactional
public class SurfaceTypeRepository extends BaseRepository<SurfaceType> {
    public SurfaceTypeRepository() {
        super(SurfaceType.class);
    }

    @Transactional(readOnly = true)
    public SurfaceType findByName(String name) {
        List<SurfaceType> results = em.createQuery("SELECT s FROM SurfaceType s WHERE s.name = :name AND s.deleted = false",
        SurfaceType.class)
        .setParameter("name", name)
        .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
