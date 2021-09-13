package com.senla.courses.shops.api.services;

import com.senla.courses.shops.model.Price;
import com.senla.courses.shops.model.Product;
import com.senla.courses.shops.model.Shop;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Service interface for {@link Price}
 */
public interface PriceService {

    Map<LocalDate, BigDecimal> getDynamics(String productName, String shopName, String shopAddress, String start, String end);

    List<PriceShop> getLastPrices(String productName);

    Price findByProductAndShopsAndDate(Product product, Shop shop, LocalDate date);

    Price save(Price price);
}
