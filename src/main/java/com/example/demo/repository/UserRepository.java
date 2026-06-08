package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.User;

import java.util.List;

/**
 * Repository for managing User entities.
 * Provides lookup by phone number and soft delete support.
 */
@Repository
@Transactional
public class UserRepository extends BaseRepository<User> {
    
    public UserRepository() {
        super(User.class);
    }

    /**
     * Finds a user by phone number.
     * Only returns non-deleted users.
     *
     * @param phoneNumber unique phone number of the user
     * @return user or null if not found
     */
    @Transactional(readOnly = true)
    public User findByPhoneNumber(String phoneNumber) {
        List<User> results = em.createQuery(
                "SELECT u FROM User u " +
                "WHERE u.phoneNumber = :number " +
                "AND u.deleted = false",
                User.class)
            .setParameter("number", phoneNumber)
            .getResultList();

        return results.isEmpty() ? null : results.get(0);
    }
}
