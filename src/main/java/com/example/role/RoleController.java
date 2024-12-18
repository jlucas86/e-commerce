package com.example.role;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/addRole")
    public void addRole(@RequestBody Role role) {
        roleService.addRole(role);
    }

    @PostMapping("/addAllRoles")
    public void addAllRoles() {
        roleService.addAllRoles();
    }

    // update

    @PutMapping("/updateRole")
    public void updateRole(@RequestBody Role role) {
        roleService.updateRole(role);
    }

    // delete

    @DeleteMapping("/deleteRole")
    public void deleteRole(@RequestBody Role role) {
        roleService.deleteRole(role);
    }

}
