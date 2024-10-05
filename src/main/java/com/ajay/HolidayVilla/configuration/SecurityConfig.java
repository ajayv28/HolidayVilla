package com.ajay.HolidayVilla.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/home.html/**")
                .permitAll()
                .requestMatchers("/css/**")
                .permitAll()
                .requestMatchers( "/js/**")
                .permitAll()
                .requestMatchers("/images/**")
                .permitAll()
                .requestMatchers("/api/guest/**")
                .hasRole("GUEST")
                .requestMatchers("/guest.html/**")
                .hasRole("GUEST")
                .requestMatchers("/api/booking/**")
                .hasAnyRole("ROOM_DIVISION","MANAGER")
                .requestMatchers("/booking.html/**")
                .hasAnyRole("ROOM_DIVISION","MANAGER")
                .requestMatchers("/api/coupon/**")
                .hasRole("MANAGER")
                .requestMatchers("/coupon.html/**")
                .hasRole("MANAGER")
                .requestMatchers("/api/food-order/**")
                .hasAnyRole("KITCHEN_FOOD","MANAGER")
                .requestMatchers("/food_order.html/**")
                .hasAnyRole("KITCHEN_FOOD","MANAGER")
                .requestMatchers("/api/maintenance/**")
                .hasAnyRole("MAINTENANCE","MANAGER")
                .requestMatchers("/maintenance.html/**")
                .hasAnyRole("MAINTENANCE","MANAGER")
                .requestMatchers("/api/material/**")
                .hasAnyRole("PURCHASE","MANAGER")
                .requestMatchers("/material.html/**")
                .hasAnyRole("PURCHASE","MANAGER")
                .requestMatchers("/api/material-requisition/**")
                .hasAnyRole("PURCHASE","MANAGER")
                .requestMatchers("/material_requisition.html/**")
                .hasAnyRole("PURCHASE","MANAGER")
                .requestMatchers("/api/room/**")
                .hasAnyRole("ROOM_DIVISION","MANAGER")
                .requestMatchers("/room.html/**")
                .hasAnyRole("ROOM_DIVISION","MANAGER")
                .requestMatchers("/api/staff/**")
                .hasAnyRole("HR","MANAGER")
                .requestMatchers("/staff.html/**")
                .hasAnyRole("HR","MANAGER")
                .requestMatchers("/api/transaction/**")
                .hasAnyRole("FINANCE","MANAGER")
                .requestMatchers("/transaction.html/**")
                .hasAnyRole("FINANCE","MANAGER")
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home.html")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .formLogin()
                .defaultSuccessUrl("/home.html", true);

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService getUserDetailsService(){
        return new CustomUserDetailsService();
    }



    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());

        return daoAuthenticationProvider;
    }
}
