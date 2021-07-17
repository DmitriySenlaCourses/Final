package com.senla.courses.shops.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * Simple JavaBean object that represents a price of a {@link Product} for a {@link Shop} and a date
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prices")
public class Price extends AEntity {
    @Column(name = "value")
    private BigDecimal value;
    @Column(name = "date")
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToMany
    @JoinTable(name = "shops_has_prices", joinColumns = @JoinColumn(name = "price_id"),
            inverseJoinColumns = @JoinColumn(name = "shop_id"))
    private Set<Shop> shops;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(value, price.value) &&
                Objects.equals(date, price.date) &&
                Objects.equals(product, price.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, date, product);
    }

    @Override
    public String toString() {
        return "Price{" +
                "value=" + value +
                ", date=" + date +
                '}';
    }
}
