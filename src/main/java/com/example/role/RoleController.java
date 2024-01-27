package com.example.role;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/getRole/{id}")
    public Optional<Role> getRole(@PathVariable("id") Integer id) {
        return roleService.getRole(id);
    }

    @GetMapping("/getAllRole")
    public List<Role> getAllRole() {
        return roleService.getAllRoles();
    }

    @PostMapping("/addRole/{role}")
    public void addRole(@PathVariable("role") String role) {
        roleService.addRole(role);
    }
}
