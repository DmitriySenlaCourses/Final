package com.senla.courses.shops.model.dto;

import com.senla.courses.shops.model.Price;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data transfer object of {@link Price}
 */
@Getter
@Setter
public class PriceDto {
    private Long id;
    private BigDecimal value;
    private LocalDate date;
}
