package com.senla.courses.shops.sevices;

import com.senla.courses.shops.api.services.AppUserService;
import com.senla.courses.shops.dao.AppUserRepository;
import com.senla.courses.shops.dao.UserRoleRepository;
import com.senla.courses.shops.model.AppUser;
import com.senla.courses.shops.model.UserRole;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of {@link AppUserService} interface
 */
@Service
@Transactional
@Log4j2
public class AppUserServiceImpl implements AppUserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AppUserRepository appUserRepository;
    private UserRoleRepository userRoleRepository;

    @Autowired
    public AppUserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, AppUserRepository appUserRepository, UserRoleRepository userRoleRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.appUserRepository = appUserRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public AppUserServiceImpl() {
    }

    @Override
    public AppUser getByName(String name) {
        AppUser appUser = appUserRepository.findByName(name);
        if (appUser == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", name));
        }
        return appUser;
    }

    @Override
    public void create(AppUser appUser, String role) {
        if (isUserExists(appUser)) {
            throw new EntityExistsException(String.format("User %s already exists", appUser.getName()));
        }

        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        Set<UserRole> roles = new HashSet<>();
        if ("ROLE_ADMIN".equals(role)) {
            roles.add(userRoleRepository.getOne(1L));
        } else {
            roles.add(userRoleRepository.getOne(2L));
        }
        appUser.setRoles(roles);
        appUserRepository.save(appUser);
        log.info(String.format("Create user %s", appUser.getName()));
    }

    @Override
    public void update(AppUser appUser, String name) {
        if (isUserExists(appUser)) {
            throw new EntityExistsException(String.format("User %s already exists", appUser.getName()));
        }
        AppUser user = getByName(name);
        user.setName(appUser.getName());
        user.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        appUserRepository.save(user);
        log.info(String.format("Update user %s", user.getName()));
    }

    private boolean isUserExists(AppUser appUser) {
        try {
            getByName(appUser.getName());
            return true;
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }
}
