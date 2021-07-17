package com.senla.courses.shops.api.services;

import java.math.BigDecimal;

/**
 * Interface for custom result in PriceRepository
 */
public interface PriceShop {
    BigDecimal getValue();
    String getDate();
    String getName();
    String getAddress();
}
