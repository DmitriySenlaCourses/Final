package com.senla.courses.shops.api.services;

import com.senla.courses.shops.model.AppUser;

/**
 * Service interface for {@link AppUser}
 */
public interface AppUserService {
    AppUser getByName(String name);

    void create(AppUser appUser, String role);

    void update(AppUser appUser, String name);

    void Listener(String message);
}
