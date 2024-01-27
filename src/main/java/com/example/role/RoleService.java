package com.example.role;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.ApplicationUserRole;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> getRole(Integer id) {
        return roleRepository.findById(id);
    }

    public void addRole(String role) {
        Role r = new Role();
        r.setName(ApplicationUserRole.CUSTOMER);
        roleRepository.save(r);
    }

}
