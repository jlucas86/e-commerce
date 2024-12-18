package com.example.role;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.permission.Permission;
import com.example.permission.PermissionService;
import com.example.security.ApplicationUserRole;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    private final PermissionService permissionService;

    @Autowired
    public RoleService(RoleRepository roleRepository, PermissionService permissionService) {
        this.roleRepository = roleRepository;
        this.permissionService = permissionService;
    }

    public Optional<Role> getRole(Integer id) {
        return roleRepository.findById(id);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void addRole(String role) {
        Role r = new Role();
        r.setName(ApplicationUserRole.CUSTOMER);
        roleRepository.save(r);
    }

    /**
     * SELLER(Sets.newHashSet(PRODUCT_READ, PRODUCT_WRITE, STORE_READ, STORE_WRITE, ORDER_READ, ORDER_WRITE, PAYMENT_READ,
            PAYMENTMETHOD_READ, USERINFO_READ, USERINFO_WRITE, ROLE_READ, ROLE_WRITE)),

    CUSTOMER(Sets.newHashSet(PRODUCT_READ, CART_READ, CART_WRITE, STORE_READ, ORDER_READ, ORDER_WRITE, PAYMENT_READ,
            PAYMENT_WRITE, PAYMENTMETHOD_READ, PAYMENTMETHOD_WRITE, USERINFO_READ, USERINFO_WRITE, ROLE_READ,
            ROLE_WRITE)),

    ADMIN(Sets.newHashSet(PRODUCT_READ, PRODUCT_WRITE, CART_READ, CART_WRITE, STORE_READ, STORE_WRITE, ORDER_READ,
            ORDER_WRITE, PAYMENT_READ, PAYMENT_WRITE, PAYMENTMETHOD_READ, PAYMENTMETHOD_WRITE, USERINFO_READ,
            USERINFO_WRITE, ROLE_READ, ROLE_WRITE, PERMISSION_READ, PERMISSION_WRITE));
     */

     /**
      * seller: 1,2,5,6,7,8,9,11,13,14,15,16
      *
      * customer: 1,3,4,5,7,8,9,10,11,12,13,14,15,16
      */

    public void addAllRoles() {
        
        // call helper functions
        addAdminRole();
        addCustomerRole();
        addSellerRole();
    }

    /**
     * helper function to add admin role to database with corect permissions
     */

    public void addAdminRole(){
        Role r = new Role();
        r.setName(ApplicationUserRole.ADMIN);
        Set<Permission> permissions = new HashSet<>();
        for(int i = 1; i < 19; ++i){
            Permission p = permissionService.getPermission(i).get();
            permissions.add(p);  
        }
        r.setPermissions(permissions);
        roleRepository.save(r);
    }

    /**
     * helper function to add seller role to database with corect permissions
     */

    public void addSellerRole(){

        int[] rPermissions = {1,2,5,6,7,8,9,11,13,14,15,16};
        Role r = new Role();
        r.setName(ApplicationUserRole.SELLER);
        Set<Permission> permissions = new HashSet<>();
        for(int i = 0; i < rPermissions.length; ++i){
            Permission p = permissionService.getPermission(rPermissions[i]).get();
            permissions.add(p);  
        }
        r.setPermissions(permissions);
        roleRepository.save(r);
    }

    /**
     * helper function to add customer role to database with corect permissions
     */

    public void addCustomerRole(){

        int[] rPermissions = {1,3,4,5,7,8,9,10,11,12,13,14,15,16};
        Role r = new Role();
        r.setName(ApplicationUserRole.CUSTOMER);
        Set<Permission> permissions = new HashSet<>();
        for(int i = 0; i < rPermissions.length; ++i){
            Permission p = permissionService.getPermission(rPermissions[i]).get();
            permissions.add(p);  
        }
        r.setPermissions(permissions);
        roleRepository.save(r);
    }

    public void addRole(Role role) {
        roleRepository.save(role);
    }

    public void updateRole(Role role) {
        // check if exists
        roleRepository.save(role);
    }

    public void deleteRole(Role role) {
        // check if exists
        roleRepository.delete(role);
    }

}
