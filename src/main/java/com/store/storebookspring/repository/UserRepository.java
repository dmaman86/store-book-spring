package com.store.storebookspring.repository;

import com.store.storebookspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User Repository interface
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * To find user by name
     * @param username string
     * @return User object
     */
    User findByUsername(String username);
}