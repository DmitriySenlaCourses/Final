package com.senla.courses.shops.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Class contains related entities {@link Category}, {@link Price}, {@link Product}, {@link Shop}
 */
@Getter
@Setter
public class AllEntities {
    private Category category;
    private Price price;
    private Product product;
    private Shop shop;
}
