package com.example.security;

import java.lang.reflect.Array;

public enum ApplicationUserPermission {
    // SELLER_READ("seller:read"),
    // SELLER_WRITE("seller:write"),
    // CUSTOMER_READ("customer:read"),
    // CUSTOMER_WRITE("customer:write"),
    PRODUCT_READ("product:read"),
    PRODUCT_WRITE("product:write"),
    CART_READ("cart:read"),
    CART_WRITE("cart:write"),
    STORE_READ("store:read"),
    STORE_WRITE("store:write"),
    ORDER_READ("order:read"),
    ORDER_WRITE("order:write"),
    PAYMENT_READ("payment:read"),
    PAYMENT_WRITE("payment:write"),
    PAYMENTMETHOD_READ("paymentmethod:read"),
    PAYMENTMETHOD_WRITE("paymentmethod:write"),
    USERINFO_READ("userinfo:read"),
    USERINFO_WRITE("userinfo:write"),
    ROLE_READ("role:read"),
    ROLE_WRITE("role:write"),
    PERMISSION_READ("permission:read"),
    PERMISSION_WRITE("permission:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public ApplicationUserPermission[] allPermissions() {
        ApplicationUserPermission[] p = {PRODUCT_READ,PRODUCT_WRITE, CART_READ, CART_WRITE, STORE_READ, STORE_WRITE, ORDER_READ, ORDER_WRITE, 
            PAYMENT_READ, PAYMENT_WRITE, PAYMENTMETHOD_READ, PAYMENTMETHOD_WRITE, USERINFO_READ, USERINFO_WRITE, ROLE_READ, ROLE_WRITE, PERMISSION_READ, PERMISSION_WRITE};
        return p;
    }

}
