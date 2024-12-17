package com.example.permission;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/permission")
public class PermissionController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/getPermission/{id}")
    public Optional<Permission> getPermisson(@PathVariable("id") Integer id) {
        return permissionService.getPermission(id);
    }

    @GetMapping("/getAllPermissions")
    public List<Permission> getAllPermissons() {
        return permissionService.getAllPermissions();
    }

    @PostMapping("/addPermission")
    public void addPermission(@RequestBody Permission permission) {
        permissionService.addPermission(permission);
    }

    @PostMapping("/addAllPermissions")
    public void addAllPermissions(){
        permissionService.addAllPermission();
    }

    @PostMapping("/updatePermission")
    public void updatePermission(@RequestBody Permission permission) {
        permissionService.updatePermission(permission);
    }

    @PostMapping("/deletePermission")
    public void deletePermission(@RequestBody Permission permission) {
        permissionService.deletePermission(permission);
    }
}
