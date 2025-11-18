package com.projectweb.marktplace.repository;

import com.projectweb.marktplace.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String username);
    Page<User> findByEmailContainingAndActiveTrue(String username, Pageable pageable);
}

