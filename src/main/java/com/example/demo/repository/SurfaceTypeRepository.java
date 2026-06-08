package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.SurfaceType;

/**
 * Repository for managing SurfaceType entities.
 * Provides custom query methods for surface type lookup.
 */
@Repository
@Transactional
public class SurfaceTypeRepository extends BaseRepository<SurfaceType> {
    public SurfaceTypeRepository() {
        super(SurfaceType.class);
    }

    /**
     * Finds a surface type by its name.
     * Only returns non-deleted entities.
     *
     * @param name name of the surface type
     * @return surface type or null if not found
     */
    @Transactional(readOnly = true)
    public SurfaceType findByName(String name) {
        List<SurfaceType> results = em.createQuery(
                "SELECT s FROM SurfaceType s " +
                "WHERE s.name = :name " +
                "AND s.deleted = false",
                SurfaceType.class)
            .setParameter("name", name)
            .getResultList();

        return results.isEmpty() ? null : results.get(0);
    }
}
