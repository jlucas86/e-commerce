package com.example.security;

import org.springframework.security.core.GrantedAuthority;

public class ApplicationGrantedAuthority implements GrantedAuthority {

    private final String role;

    public ApplicationGrantedAuthority(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
