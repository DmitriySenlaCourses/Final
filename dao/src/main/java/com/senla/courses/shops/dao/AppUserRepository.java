package com.senla.courses.shops.dao;

import com.senla.courses.shops.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO interface for {@link AppUser} entity
 */
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByName(String name);
}
