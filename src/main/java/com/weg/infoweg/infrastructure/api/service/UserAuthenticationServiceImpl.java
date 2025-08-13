package com.weg.infoweg.infrastructure.api.service;

import com.weg.infoweg.core.UserAuthenticationService;
import com.weg.infoweg.infrastructure.security.user.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public final class UserAuthenticationServiceImpl implements UserAuthenticationService {

    @Override
    public UUID getIdUserAuthentication() {
        UserDetailsImpl userDetails = getUserDetails();
        return userDetails.getId();
    }

    @Override
    public UserDetailsImpl getUserDetails() {
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
