package com.senla.courses.shops.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

/**
 * Simple JavaBean object that represents a shop
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shops")
public class Shop extends AEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @ManyToMany
    @JoinTable(name = "products_has_shops", joinColumns = @JoinColumn(name = "shop_id"),
                inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products;
    @ManyToMany
    @JoinTable(name = "shops_has_prices", joinColumns = @JoinColumn(name = "shop_id"),
                inverseJoinColumns = @JoinColumn(name = "price_id"))
    private Set<Price> prices;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shop shop = (Shop) o;
        return Objects.equals(name, shop.name) &&
                Objects.equals(address, shop.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    @Override
    public String toString() {
        return "Shop{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
