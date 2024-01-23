package com.example.security;

public enum ApplicationUserPermission {
    // SELLER_READ("seller:read"),
    // SELLER_WRITE("seller:write"),
    // CUSTOMER_READ("customer:read"),
    // CUSTOMER_WRITE("customer:write"),
    // PRODUCT_READ("product:read"),
    // PRODUCT_WRITE("product:write"),
    CART_READ("product:read"),
    CART_WRITE("product:write"),
    STORE_READ("product:read"),
    STORE_WRITE("product:write"),
    ORDER_READ("product:read"),
    ORDER_WRITE("product:write"),
    PAYMENT_READ("product:read"),
    PAYMENT_WRITE("product:write"),
    PAYMENTMETHOD_READ("product:read"),
    PAYMENTMETHOD_WRITE("product:write"),
    USERINFO_READ("product:read"),
    USERINFO_WRITE("product:write"),
    ROLE_READ("product:read"),
    ROLE_WRITE("product:write"),
    PERMISSION_READ("product:read"),
    PERMISSION_WRITE("product:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
