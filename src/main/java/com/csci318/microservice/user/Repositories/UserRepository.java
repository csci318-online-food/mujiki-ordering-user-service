package com.csci318.microservice.user.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csci318.microservice.user.Domain.Entities.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT user FROM User user WHERE user.username = :username")
    Optional<User> findByUsername(String username);
}
