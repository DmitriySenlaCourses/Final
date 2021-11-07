package com.senla.courses.shops.sevices;

import com.senla.courses.shops.api.services.AppUserService;
import com.senla.courses.shops.dao.AppUserRepository;
import com.senla.courses.shops.dao.UserRoleRepository;
import com.senla.courses.shops.model.AppUser;
import com.senla.courses.shops.model.UserRole;
import com.senla.courses.shops.model.dto.AppUserDto;
import com.senla.courses.shops.sevices.token.TokenProvider;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
    private ModelMapper modelMapper;
    private TokenProvider tokenProvider;
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @Autowired
    public AppUserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, AppUserRepository appUserRepository, UserRoleRepository userRoleRepository,
                              ModelMapper modelMapper, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder,
                              KafkaTemplate<Integer, String> kafkaTemplate) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.appUserRepository = appUserRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.kafkaTemplate = kafkaTemplate;
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
    public void create(AppUserDto appUserDto, String role) {
        if (isUserExists(appUserDto)) {
            throw new EntityExistsException(String.format("User %s already exists", appUserDto.getName()));
        }

        AppUser appUser = modelMapper.map(appUserDto, AppUser.class);
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        Set<UserRole> roles = new HashSet<>();
        if ("ROLE_ADMIN".equals(role)) {
            roles.add(userRoleRepository.getOne(1L));
        } else {
            roles.add(userRoleRepository.getOne(2L));
        }
        appUser.setRoles(roles);
        appUserRepository.save(appUser);

        kafkaTemplate.send("myTopic", appUser.getName());
    }

    @Override
    public void update(AppUserDto appUserDto, String updatedName) {
        if (isUserExists(appUserDto)) {
            throw new EntityExistsException(String.format("User %s already exists", appUserDto.getName()));
        }


        AppUser updatedUser = getByName(updatedName);
        updatedUser.setName(appUserDto.getName());
        updatedUser.setPassword(bCryptPasswordEncoder.encode(appUserDto.getPassword()));
        appUserRepository.save(updatedUser);
        log.info(String.format("Update user %s", updatedUser.getName()));
    }

    @Override
    public String login(AppUserDto appUserDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(appUserDto.getName(), appUserDto.getPassword());
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        AppUser appUser = appUserRepository.findByName(appUserDto.getName());
        return tokenProvider.createToken(appUser.getName(), appUser.getRoles().iterator().next().getName());
    }

    private boolean isUserExists(AppUserDto appUserDto) {
        try {
            getByName(appUserDto.getName());
            return true;
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }

    @KafkaListener(topics = "myTopic")
    @Override
    public void listener(String message) {
        log.info(String.format("Create user %s", message));
    }
}
