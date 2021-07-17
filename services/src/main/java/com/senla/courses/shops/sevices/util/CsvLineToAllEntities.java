package com.senla.courses.shops.sevices.util;

import com.senla.courses.shops.model.AllEntities;
import com.senla.courses.shops.model.Category;
import com.senla.courses.shops.model.Price;
import com.senla.courses.shops.model.Product;
import com.senla.courses.shops.model.Shop;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for convert csv line to {@link AllEntities}
 */
public class CsvLineToAllEntities {
    private CsvLineToAllEntities() {
    }

    public static AllEntities convert(String[] data) {
        Category category = new Category();
        category.setName(data[2]);

        Price price = new Price();
        price.setValue(BigDecimal.valueOf(Long.parseLong(data[3])));
        price.setDate(LocalDate.parse(data[4], DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        Product product = new Product();
        product.setName(data[0]);
        product.setUnit(data[1]);

        Shop shop = new Shop();
        shop.setName(data[5]);
        shop.setAddress(data[6]);

        AllEntities allEntities = new AllEntities();
        allEntities.setCategory(category);
        allEntities.setPrice(price);
        allEntities.setProduct(product);
        allEntities.setShop(shop);
        return allEntities;
    }
}
