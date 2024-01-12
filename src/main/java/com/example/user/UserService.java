package com.example.user;

import java.util.Optional;

import org.checkerframework.checker.units.qual.radians;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUser(Integer id) {
        return userRepository.findById(id);
    }
}
