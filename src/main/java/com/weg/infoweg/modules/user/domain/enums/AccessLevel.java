package com.weg.infoweg.modules.user.domain.enums;

import org.springframework.security.core.GrantedAuthority;

public enum AccessLevel implements GrantedAuthority {
    STUDENT("ROLE_STUDENT"),
    TEACHER("ROLE_TEACHER"),
    COORDINATOR("ROLE_COORDINATOR"),
    ADMINISTRATOR("ROLE_ADMINISTRATOR"),
    SECRETARY("ROLE_SECRETARY");

    final String role;

    AccessLevel(String role){
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return "";
    }
}