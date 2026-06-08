package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.BaseEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Generic base repository providing common CRUD operations
 * with support for soft delete.
 *
 * @param <T> entity type extending BaseEntity
 */
public abstract class BaseRepository<T extends BaseEntity> {
    @PersistenceContext
    protected EntityManager em;

    private final Class<T> entityClass;

    protected BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Saves or updates an entity.
     *
     * @param entity entity to persist or merge
     * @return saved entity
     */
    public T save(T entity) {
        if (entity.getId() == null) {
            em.persist(entity);
            return entity;
        }
        return em.merge(entity);
    }

    /**
     * Returns all non-deleted entities.
     *
     * @return list of active entities
     */
    public List<T> findAll() {
        return em.createQuery(
                "SELECT e FROM " +
                entityClass.getSimpleName() +
                " e WHERE e.deleted = false",
            entityClass)
            .getResultList();
    }

    /**
     * Finds entity by id if not soft-deleted.
     *
     * @param id entity identifier
     * @return entity or null if not found or deleted
     */
    public T findById(Long id) {
        T entity = em.find(entityClass, id);
        return entity != null && entity.isDeleted() ? null : entity;
    }

    /**
     * Soft deletes an entity by marking it as deleted.
     *
     * @param id entity identifier
     */
    public void delete(Long id) {
        T entity = findById(id);
        
        if(entity != null) {
            entity.setDeleted(true);
            em.merge(entity);
        }
    }
}
