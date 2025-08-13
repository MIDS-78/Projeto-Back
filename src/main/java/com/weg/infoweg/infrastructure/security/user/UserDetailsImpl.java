package com.weg.infoweg.infrastructure.security.user;

import com.weg.infoweg.modules.user.domain.enums.AccessLevel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class UserDetailsImpl implements UserDetails {

    private UUID id;
    private AccessLevel accessLevel;
    private String password;
    private String username;

    public UUID getId(){
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(accessLevel);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
