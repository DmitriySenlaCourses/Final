package com.senla.courses.shops.dao;

import com.senla.courses.shops.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO interface for {@link Category} entity
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByNameEquals(String name);
}
