package com.ramsa.company.repository;

import com.ramsa.company.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Page<Role> findByNameContaining(String name, Pageable pageable);
}