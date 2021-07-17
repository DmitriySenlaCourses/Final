package com.senla.courses.shops.dao;

import com.senla.courses.shops.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO interface for {@link Shop} entity
 */
@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    Shop findByNameAndAddress(String name, String address);
}
