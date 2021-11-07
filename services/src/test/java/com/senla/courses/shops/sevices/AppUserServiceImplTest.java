package com.senla.courses.shops.sevices;

import com.senla.courses.shops.api.services.AppUserService;
import com.senla.courses.shops.dao.AppUserRepository;
import com.senla.courses.shops.dao.UserRoleRepository;
import com.senla.courses.shops.model.AppUser;
import com.senla.courses.shops.model.UserRole;
import com.senla.courses.shops.model.dto.AppUserDto;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import static org.hamcrest.CoreMatchers.is;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityExistsException;

public class AppUserServiceImplTest {

    @InjectMocks
    private AppUserService appUserService = new AppUserServiceImpl();

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private UserRoleRepository userRoleRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private KafkaTemplate<Integer, String> kafkaTemplate;

    public AppUserServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getByName() {
        AppUser appUser = new AppUser();
        String name = "user";
        Mockito.when(appUserRepository.findByName(Mockito.anyString())).thenReturn(appUser);

        AppUser returnedUser = appUserService.getByName(name);

        Mockito.verify(appUserRepository, Mockito.times(1)).findByName(Mockito.anyString());
        Assert.assertEquals(appUser, returnedUser);

    }

    @Test
    public void getByNameException() {
        AppUser appUser = null;
        String name = "user";
        Mockito.when(appUserRepository.findByName(Mockito.anyString())).thenReturn(appUser);

        try {
            AppUser returnedUser = appUserService.getByName(name);
            Assert.fail("Expected an UsernameNotFoundException to be thrown");
        } catch (UsernameNotFoundException e) {
            Assert.assertThat(e.getMessage(), is(String.format("User %s not found", name)));
        }

        Mockito.verify(appUserRepository, Mockito.times(1)).findByName(Mockito.anyString());

    }

    @Test
    public void create() {
        UserRole userRole = new UserRole();
        AppUserDto appUserDto = new AppUserDto();
        appUserDto.setPassword("pass");
        appUserDto.setName("name");
        AppUser appUser = new AppUser();
        appUser.setPassword("pass");
        appUser.setName("name");
        Mockito.when(appUserRepository.findByName(Mockito.anyString())).thenReturn(null);
        Mockito.when(modelMapper.map(Mockito.any(AppUserDto.class), Mockito.eq(AppUser.class))).thenReturn(appUser);
        Mockito.when(bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn("password");
        Mockito.when(userRoleRepository.getOne(Mockito.anyLong())).thenReturn(userRole);
        Mockito.when(appUserRepository.save(appUser)).thenReturn(appUser);
        Mockito.when(kafkaTemplate.send(Mockito.anyString(), Mockito.anyString())).thenReturn(Mockito.any());

        appUserService.create(appUserDto, "ROLE_USER");

        Mockito.verify(appUserRepository, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(modelMapper, Mockito.times(1)).map(Mockito.any(AppUserDto.class), Mockito.eq(AppUser.class));
        Mockito.verify(bCryptPasswordEncoder, Mockito.times(1)).encode(Mockito.anyString());
        Mockito.verify(appUserRepository, Mockito.times(1)).save(appUser);
        Mockito.verify(userRoleRepository, Mockito.times(1)).getOne(Mockito.anyLong());
        Assert.assertEquals("password", appUser.getPassword());
        Assert.assertEquals(1, appUser.getRoles().size());
    }

    @Test
    public void createException() {
        UserRole userRole = new UserRole();
        AppUserDto appUserDto = new AppUserDto();
        appUserDto.setPassword("pass");
        appUserDto.setName("name");
        AppUser appUser = new AppUser();
        Mockito.when(appUserRepository.findByName(Mockito.anyString())).thenReturn(appUser);
        Mockito.when(modelMapper.map(Mockito.any(AppUserDto.class), Mockito.eq(AppUser.class))).thenReturn(appUser);
        Mockito.when(bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn("password");
        Mockito.when(userRoleRepository.getOne(Mockito.anyLong())).thenReturn(userRole);
        Mockito.when(appUserRepository.save(appUser)).thenReturn(appUser);

        try {
            appUserService.create(appUserDto, "ROLE_USER");
            Assert.fail("Expected an EntityExistsException to be thrown");
        } catch (EntityExistsException e) {
            Assert.assertThat(e.getMessage(), is(String.format("User %s already exists", appUserDto.getName())));
        }

        Mockito.verify(appUserRepository, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(modelMapper, Mockito.never()).map(Mockito.any(AppUserDto.class), Mockito.eq(AppUser.class));
        Mockito.verify(bCryptPasswordEncoder, Mockito.never()).encode(Mockito.anyString());
        Mockito.verify(appUserRepository, Mockito.never()).save(appUser);
        Mockito.verify(userRoleRepository, Mockito.never()).getOne(Mockito.anyLong());
    }

    @Test
    public void update() {
        AppUser updateUser = new AppUser();
        AppUserDto appUserDto = new AppUserDto();
        appUserDto.setPassword("pass");
        appUserDto.setName("name");
        Mockito.when(appUserRepository.findByName(Mockito.anyString())).thenReturn(null, updateUser);
        Mockito.when(bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn("password");
        Mockito.when(appUserRepository.save(Mockito.any(AppUser.class))).thenReturn(updateUser);

        appUserService.update(appUserDto, "user");

        Mockito.verify(appUserRepository, Mockito.times(2)).findByName(Mockito.anyString());
        Mockito.verify(bCryptPasswordEncoder, Mockito.times(1)).encode(Mockito.anyString());
        Mockito.verify(appUserRepository, Mockito.times(1)).save(updateUser);
        Assert.assertEquals("password", updateUser.getPassword());
        Assert.assertEquals("name", updateUser.getName());
    }

    @Test(expected = EntityExistsException.class)
    public void updateException() {
        AppUser appUser = new AppUser();
        AppUser updateUser = new AppUser();
        AppUserDto appUserDto = new AppUserDto();
        appUserDto.setPassword("pass");
        appUserDto.setName("name");
        Mockito.when(appUserRepository.findByName(Mockito.anyString())).thenReturn(appUser, updateUser);
        Mockito.when(bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn("password");
        Mockito.when(appUserRepository.save(Mockito.any(AppUser.class))).thenReturn(updateUser);

        appUserService.update(appUserDto, "user");

        Mockito.verify(appUserRepository, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(bCryptPasswordEncoder, Mockito.never()).encode(Mockito.anyString());
        Mockito.verify(appUserRepository, Mockito.never()).save(updateUser);
    }
}