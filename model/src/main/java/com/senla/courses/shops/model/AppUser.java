package com.senla.courses.shops.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Simple JavaBean object that represents a AppUser.
 */
@Entity
@Table(name = "users")
@Getter
@Setter

public class AppUser extends AEntity {
    @Column(name = "name")
    private String name;
    @ManyToMany
    @JoinTable(name = "users_has_roles", joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    @ApiModelProperty(hidden = true)
    private Set<UserRole> roles;
    @Column(name = "password")
    private String password;
}
