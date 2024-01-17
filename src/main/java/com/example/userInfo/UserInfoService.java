package com.example.userInfo;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.exceptions.EmailAlreadyExists;
import com.example.exceptions.InvalidPassword;
import com.example.exceptions.UsernameAlreadyExists;
import com.example.permission.Permission;
import com.example.role.Role;
import com.example.security.ApplicationUserPermission;
import com.example.security.ApplicationUserRole;

@Service
public class UserInfoService {

    private final UserInfoRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserInfoService(UserInfoRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // public UserInfoService(UserInfoRepository userRepository) {
    // this.userRepository = userRepository;
    // }

    public Optional<UserInfo> getUser(Integer id) {
        return userRepository.findById(id);
    }

    public void addUser(UserInfo user) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++" + user.getPassword());

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
        hold.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++" + hold);

        try {
            validateUserInfo(user);
            System.out.println("i made it");
            userRepository.save(hold);

        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e.getMessage() + "++++++++++++++++++++++++++++++++++++++++++ urg");
        }

    }

    public Boolean validateUserInfo(UserInfo user) throws UsernameAlreadyExists, EmailAlreadyExists, InvalidPassword {

        if (userRepository.existsByUsername(user.getUsername())) {

            throw new UsernameAlreadyExists(String.format("Username %s already exists", user.getUsername()));

        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExists(String.format("Email %s already exists", user.getEmail()));
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidPassword("Password is too short");
        }
        if (user.getPassword().length() > 20) {
            throw new InvalidPassword("Password is too long");
        }
        return true;
    }
}
