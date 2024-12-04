package com.ramsa.company.repository;

import com.ramsa.company.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); // Custom query method to find a user by username

    Page<User> findByUsernameContaining(String username, Pageable pageable);
}