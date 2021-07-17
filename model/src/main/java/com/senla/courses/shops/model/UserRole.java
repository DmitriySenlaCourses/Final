package com.senla.courses.shops.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Simple JavaBean object that represents a role of {@link AppUser}
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
public class UserRole extends AEntity {
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<AppUser> users;
}
