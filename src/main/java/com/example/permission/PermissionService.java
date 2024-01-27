package com.example.permission;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.ApplicationUserPermission;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Optional<Permission> getPermission(Integer id) {
        return permissionRepository.findById(id);
    }

    public void addPermission(String permission) {
        Permission pHold = new Permission();
        pHold.setPermission(ApplicationUserPermission.PRODUCT_READ);
        permissionRepository.save(pHold);
    }

}
