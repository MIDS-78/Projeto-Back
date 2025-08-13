package com.weg.infoweg.core;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserAuthenticationService {

    UUID getIdUserAuthentication();

    UserDetails getUserDetails();
}
