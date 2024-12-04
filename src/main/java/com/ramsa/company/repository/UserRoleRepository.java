package com.ramsa.company.repository;

import com.ramsa.company.entity.UserRole;
import com.ramsa.company.entity.UserRoleId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    // You can define custom query methods here if needed
}