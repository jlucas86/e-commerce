package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.permission.PermissionService;
import com.example.product.ProductService;
import com.example.role.RoleRepository;
import com.example.role.RoleService;
import com.example.store.StoreService;
import com.example.userInfo.UserInfoService;

import jakarta.annotation.PostConstruct;

@Component
public class intitate {
    
    private final PermissionService permissionService;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final UserInfoService userInfoService;

    private final StoreService storeService;

    private final ProductService productService;

    
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public intitate(PermissionService permissionService, RoleService roleService, RoleRepository roleRepository, UserInfoService userInfoService, StoreService storeService, ProductService productService, PasswordEncoder passwordEncoder) {
        this.permissionService = permissionService;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.userInfoService = userInfoService;
        this.storeService = storeService;
        this.productService = productService;
        this.passwordEncoder = passwordEncoder;
    }
    

    // @EventListener(ApplicationReadyEvent.class)
    @PostConstruct
    public void  startUp(){
        System.err.println("entered intiate method");
        permissionService.addAllPermission();
        roleService.addAllRoles();
    }

}
