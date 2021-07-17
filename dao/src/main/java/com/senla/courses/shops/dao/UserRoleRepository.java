package com.senla.courses.shops.dao;

import com.senla.courses.shops.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO interface for {@link UserRole} entity
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
