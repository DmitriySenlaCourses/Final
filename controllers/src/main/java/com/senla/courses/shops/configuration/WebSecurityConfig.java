package com.senla.courses.shops.configuration;

import com.senla.courses.shops.exceptionhendlers.JwtAccessDeniedHandler;
import com.senla.courses.shops.exceptionhendlers.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@ComponentScan("com.senla.courses.shops.sevices")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private TokenFilter tokenFilter;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    public WebSecurityConfig(TokenFilter tokenFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.tokenFilter = tokenFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.POST, "/users/login").permitAll()
                .antMatchers(HttpMethod.PUT, "/users").authenticated()
                .antMatchers(HttpMethod.GET, "/kafka").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST,"/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
