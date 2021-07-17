package com.senla.courses.shops.dao;

import com.senla.courses.shops.model.Category;
import com.senla.courses.shops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO interface for {@link Product} entity
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(long id);
    List<Product> findByCategory(Category category);
    List<Product> findByCategoryAndNameContaining(Category category, String name);
    Product findByNameEquals(String name);
}
