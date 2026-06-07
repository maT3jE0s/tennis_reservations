package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.User;

import java.util.List;

@Repository
@Transactional
public class UserRepository extends BaseRepository<User> {
    
    public UserRepository() {
        super(User.class);
    }

    @Transactional(readOnly = true)
    public User findByPhoneNumber(String phoneNumber) {
        List<User> results = em.createQuery("SELECT u FROM User u WHERE u.phoneNumber = :number AND u.deleted = false",
        User.class)
        .setParameter("number", phoneNumber)
        .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
