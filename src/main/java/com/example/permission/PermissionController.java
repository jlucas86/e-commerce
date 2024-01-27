package com.example.permission;

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

    @PostMapping("/addPermission/{permission}")
    public void addPermission(@PathVariable("permission") String permission) {
        permissionService.addPermission(permission);
    }
}
