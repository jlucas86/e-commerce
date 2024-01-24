package com.example.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

import static com.example.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    SELLER(Sets.newHashSet(PRODUCT_READ, PRODUCT_WRITE, STORE_READ, STORE_WRITE, ORDER_READ, ORDER_WRITE, PAYMENT_READ,
            PAYMENTMETHOD_READ, USERINFO_READ, USERINFO_WRITE, ROLE_READ, ROLE_WRITE)),
    CUSTOMER(Sets.newHashSet(PRODUCT_READ, CART_READ, CART_WRITE, STORE_READ, ORDER_READ, ORDER_WRITE, PAYMENT_READ,
            PAYMENT_WRITE, PAYMENTMETHOD_READ, PAYMENTMETHOD_WRITE, USERINFO_READ, USERINFO_WRITE, ROLE_READ,
            ROLE_WRITE)),
    ADMIN(Sets.newHashSet(PRODUCT_READ, PRODUCT_WRITE, CART_READ, CART_WRITE, STORE_READ, STORE_WRITE, ORDER_READ,
            ORDER_WRITE, PAYMENT_READ, PAYMENT_WRITE, PAYMENTMETHOD_READ, PAYMENTMETHOD_WRITE, USERINFO_READ,
            USERINFO_WRITE, ROLE_READ, ROLE_WRITE, PERMISSION_READ, PERMISSION_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }

}
