package com.pmesmart.smartapi.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import com.pmesmart.smartapi.model.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsData implements UserDetails {

    private final Optional<User> user;

    public UserDetailsData(Optional<User> user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return user.orElse(new User()).getPassword();
    }

    @Override
    public String getUsername() {
        return user.orElse(new User()).getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO implementar campos no user para salvar na BD  o estado
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO implementar campos no user para salvar na BD  o estado
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO implementar campos no user para salvar na BD  o estado
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO implementar campos no user para salvar na BD  o estado
        return true;
    }
     
}
