package com.example.auth;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;

import com.example.user.UserRepository;

public class ApplicationUserManager {

    private final UserRepository userRepository;

    public void createUser(ApplicationUser user) {
        User hold = new User(null, user.getEmail(), user.getUsername(), user.getPassword(), user.getAuthorities())
        userRepository.save(user);
    }

    public void updateUser(ApplicationUser user) {
    }

    @Override
    public void deleteUser(String username) {
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
    }

    @Override
    public boolean userExists(String username) {
    }
}
