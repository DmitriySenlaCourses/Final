package com.senla.courses.shops.sevices;

import com.senla.courses.shops.dao.AppUserRepository;
import com.senla.courses.shops.model.AppUser;
import com.senla.courses.shops.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of {@link UserDetailsService} interface
 */
@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

    AppUserRepository appUserRepository;

    @Autowired
    public UserDetailServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByName(username);

        Set<GrantedAuthority> authorities = new HashSet<>();

        for (UserRole role : appUser.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new User(appUser.getName(), appUser.getPassword(), authorities);
    }
}
