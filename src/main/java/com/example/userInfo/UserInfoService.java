package com.example.userInfo;

import java.util.HashSet;
import java.util.List;
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
import com.example.role.RoleRepository;
import com.example.security.ApplicationUserPermission;
import com.example.security.ApplicationUserRole;

@Service
public class UserInfoService {

    private final UserInfoRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserInfoService(UserInfoRepository userRepository, PasswordEncoder passwordEncoder,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    // public UserInfoService(UserInfoRepository userRepository) {
    // this.userRepository = userRepository;
    // }

    public Optional<UserInfo> getUser(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<UserInfo> getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserInfo> getAllUser() {
        return userRepository.findAll();
    }

    public void addUser(UserInfo user) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++" + user.getUsername());

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
        Role r = null;
        if (user.getUsername().equals("jimmithy")) {
            r = roleRepository.findById(1).get();
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++" + r);
        Set<Role> role = new HashSet<Role>();
        role.add(r);
        hold.setRoles(role);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++" + r);

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
        /*
         * This is where passwords will be screed to ensure they are strong and do not
         * violate rules
         * 1. No chains of numbers >=3 that are inorder
         * 2. No chains of letters >=4 that are inorder
         * 3. Must contain at least one capital letter
         * 4. Must contain at least one lowercase letter
         * 5. Must contain at least one symbol "!@#$%&"
         * 6. Must be longet than 6 caracters but shorter than 20
         * 7. screen for common passwords such as "password"
         */
        return true;
    }

    public void updateUser(UserInfo user) {
        userRepository.save(user);
    }

    public void deleteUser(UserInfo user) {
        userRepository.delete(user);
    }
}
