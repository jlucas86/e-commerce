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

    public void addPermission(Permission permission) {
        permissionRepository.save(permission);
    }

    public void updatePermission(Permission permission) {
        // check if exists
        permissionRepository.save(permission);
    }

    public void deletePermission(Permission permission) {
        // check if exists
        permissionRepository.delete(permission);
    }

}
