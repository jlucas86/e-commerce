package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.permission.PermissionService;
import com.example.role.RoleService;

import jakarta.annotation.PostConstruct;

@Component
public class intitate {
    
    private final PermissionService permissionService;
    private final RoleService roleService;

    @Autowired
    public intitate(PermissionService permissionService, RoleService roleService) {
        this.permissionService = permissionService;
        this.roleService = roleService;
    }

    // @EventListener(ApplicationReadyEvent.class)
    @PostConstruct
    public void  startUp(){
        System.err.println("entered intiate method");
        permissionService.addAllPermission();
        roleService.addAllRoles();
    }


}
