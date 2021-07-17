package com.senla.courses.shops.api.services;

import com.senla.courses.shops.model.Shop;
import com.senla.courses.shops.model.dto.ShopDto;

import java.util.List;

/**
 * Service interface for {@link Shop}
 */
public interface ShopService {
    ShopDto create(ShopDto shopDto);

    List<ShopDto> getAll();

}
