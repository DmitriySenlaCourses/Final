package com.senla.courses.shops.dao;

import com.senla.courses.shops.api.services.PriceShop;
import com.senla.courses.shops.model.Price;
import com.senla.courses.shops.model.Product;
import com.senla.courses.shops.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * DAO interface for {@link Price} entity
 */
@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByProductAndShopsAndDateBetweenOrderByDate(Product product, Shop shop, LocalDate start, LocalDate end);

    Price findByProductAndShopsAndDate(Product product, Shop shop, LocalDate date);

    @Query(value = "SELECT p.value, p.date, s.name, s.address \n" +
            "FROM prices AS p\n" +
            "LEFT JOIN shops_has_prices AS sp ON p.id = sp.price_id \n" +
            "LEFT JOIN shops AS s ON s.id = sp.shop_id\n" +
            "WHERE product_id = ?1 AND (sp.shop_id, p.date) IN (\n" +
            "SELECT sp.shop_id, max(p.date) \n" +
            "FROM prices AS p, shops_has_prices AS sp \n" +
            "WHERE product_id = ?1 AND p.id = sp.price_id\n" +
            "GROUP BY sp.shop_id)", nativeQuery = true)
    List<PriceShop> getLastPrices(Long productId);


}
