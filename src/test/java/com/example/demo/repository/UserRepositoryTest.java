package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.example.demo.entity.User;

@DataJpaTest
@Import(UserRepository.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setup() {
        user = userRepository.save(new User("0908123456", "Jan"));
    }

    @Test
    void testFindByPhoneNumber() {
        User result = userRepository.findByPhoneNumber("0908123456");
        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void testFindByPhoneNumber_notFound() {
        User result = userRepository.findByPhoneNumber("9999999999");
        assertNull(result);
    }
}
