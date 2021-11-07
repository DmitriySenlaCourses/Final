package com.senla.courses.shops.controllers;

import com.senla.courses.shops.api.services.AppUserService;
import com.senla.courses.shops.model.AppUser;
import com.senla.courses.shops.model.dto.AppUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RestController for {@link AppUser}
 */
@RestController
@RequestMapping("/users")
@Api(tags = {"User Controller. Some operations on the user"})
public class AppUserController {

    private AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping
    @ApiOperation(value = "Create new user")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "User was created successfully"),
            @ApiResponse(code = 400, message = "User already exists")})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> create(@RequestBody AppUserDto appUserDto, Authentication authentication) {
        String role = "ROLE_USER";
        if (authentication != null) {
            GrantedAuthority authority = authentication.getAuthorities().iterator().next();
            role = authority.getAuthority();
        }
        appUserService.create(appUserDto, role);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @ApiOperation(value = "Update user")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "User was updated successfully"),
            @ApiResponse(code = 400, message = "User already exists")})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> update(@RequestBody AppUserDto appUserDto, Authentication authentication) {
        appUserService.update(appUserDto, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AppUserDto appUserDto) {

        String token = appUserService.login(appUserDto);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/logout")
    @ApiOperation(value = "Logout user")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Successful logout")})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        return ResponseEntity.noContent().build();
    }
}
