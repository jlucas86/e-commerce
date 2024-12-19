package com.example.userInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    // get

    public Optional<UserInfo> getUser(Integer id) {
        return userRepository.findById(id);
    }

    public UserInfo getUser(String username) throws UsernameNotFoundException {
        Optional<UserInfo> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            UserInfo u = user.get();
            u.setPassword("***********");
            return u;
        }

        throw new UsernameNotFoundException(String.format("Username %s already exists", username));
    }

    public List<UserInfo> getAllUser() {
        return userRepository.findAll();
    }

    public Boolean isLoggedIn(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication.getName().equals("anonymousUser"))
            return false;
        else
            return true;
    }

    // add

    public void addUser(UserInfo user) throws UsernameAlreadyExists, EmailAlreadyExists, InvalidPassword {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++" + user.getUsername());

        // Role role = new Role(0, ApplicationUserRole.CUSTOMER, permissions);
        // Set<Role> roles;
        // roles.add(role);

        UserInfo hold = new UserInfo();
        hold.setEmail(user.getEmail());
        hold.setUsername(user.getUsername());
        hold.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++" + hold);
        // Role r = null;
        // if (user.getUsername().equals("jimmithy")) {
        // r = roleRepository.findById(1).get();
        // }

        // role
        Set<Role> role = new HashSet<Role>();
        for (Role r : user.getRoles()) {
            if (roleRepository.findById(r.getId()).isPresent())
                role.add(roleRepository.findById(r.getId()).get());
            else 
                return;
                //trow error
        }
        hold.setRoles(role);

        validateUserInfo(user);

        System.out.println("i made it");
        userRepository.save(hold);

    }

    // update

    public void updateUser(UserInfo user) throws UsernameAlreadyExists, EmailAlreadyExists, InvalidPassword {
        validateUserInfo(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    // delete

    public void deleteUser(UserInfo user) throws UsernameNotFoundException {
        if (!userRepository.existsByUsername(user.getUsername()))
            throw new UsernameNotFoundException(String.format("Username %s not found", user.getUsername()));
        userRepository.delete(user);
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
}
