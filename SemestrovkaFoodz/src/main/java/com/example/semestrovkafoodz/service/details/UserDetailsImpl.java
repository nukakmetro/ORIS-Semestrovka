package com.example.semestrovkafoodz.service.details;

import com.example.semestrovkafoodz.entities.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class UserDetailsImpl implements UserDetails {

    private UserEntity userEntity;

    public UserDetailsImpl(UserEntity userEntity) { this.userEntity = userEntity; }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userEntity.getRole().toString());
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() { return userEntity.getPassword(); }

    @Override
    public String getUsername() { return userEntity.getUsername(); }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return userEntity.isActive(); }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return userEntity.isActive(); }
}
