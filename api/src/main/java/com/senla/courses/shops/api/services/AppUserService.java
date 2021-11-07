package com.senla.courses.shops.api.services;

import com.senla.courses.shops.model.AppUser;
import com.senla.courses.shops.model.dto.AppUserDto;

/**
 * Service interface for {@link AppUser}
 */
public interface AppUserService {
    AppUser getByName(String name);

    void create(AppUserDto appUserDto, String role);

    void update(AppUserDto appUserDto, String name);

    String login(AppUserDto appUserDto);

    void listener(String message);
}
