package com.example.userInfo;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.permission.Permission;
import com.example.role.Role;
import com.example.security.ApplicationUserPermission;
import com.example.security.ApplicationUserRole;

@Service
public class UserInfoService {

    private final UserInfoRepository userRepository;

    @Autowired
    public UserInfoService(UserInfoRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserInfo> getUser(Integer id) {
        return userRepository.findById(id);
    }

    public void addUser(UserInfo user) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++" + user.getEmail());

        // Permission permission = new Permission(0,
        // ApplicationUserPermission.CUSTOMER_READ);
        // Set<Permission> permissions = null;
        // permissions.add(permission);
        // Role role = new Role(0, ApplicationUserRole.CUSTOMER, permissions);
        // Set<Role> roles;
        // roles.add(role);
        UserInfo hold = new UserInfo();
        hold.setEmail(user.getEmail());
        hold.setUsername(user.getUsername());
        hold.setPassword(null);
        hold.setRoles(null);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++" + hold);
        userRepository.save(hold);
    }
}
