package com.csci318.microservice.user.Repositories;

import com.csci318.microservice.user.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
