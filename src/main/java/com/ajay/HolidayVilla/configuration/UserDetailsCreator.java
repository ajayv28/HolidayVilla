package com.ajay.HolidayVilla.configuration;

import com.ajay.HolidayVilla.model.Guest;
import com.ajay.HolidayVilla.model.Staff;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsCreator implements UserDetails {

    String username;

    String password;

    List<GrantedAuthority> authorities;

    public UserDetailsCreator(Object object){
        if(object instanceof Staff){
            Staff staff = (Staff)object;
            this.username = staff.getEmail();
            this.password = staff.getPassword();
            this.authorities = new ArrayList<>();
            String roles[] = staff.getRole().split(",");

            for(String role : roles){
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
                authorities.add(simpleGrantedAuthority);
            }

        }else if(object instanceof Guest){
            Guest guest = (Guest)object;
            this.username = guest.getEmail();
            this.password = guest.getPassword();
            this.authorities = new ArrayList<>();
            String roles[] = guest.getRole().split(",");

            for(String role : roles){
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
                authorities.add(simpleGrantedAuthority);
            }
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
