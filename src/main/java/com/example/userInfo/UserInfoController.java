package com.example.userInfo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptions.EmailAlreadyExists;
import com.example.exceptions.InvalidPassword;
import com.example.exceptions.UsernameAlreadyExists;

@RestController
@RequestMapping("/user")
public class UserInfoController {

    private final UserInfoService userService;

    @Autowired
    public UserInfoController(UserInfoService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUsername/{username}")
    @PreAuthorize("#username == authentication.principal.username")
    public UserInfo getUser(@PathVariable("username") String username) {
        return userService.getUser(username);
    }

    @GetMapping("/getAllUsers")
    public List<UserInfo> getAllUsers() {
        return userService.getAllUser();
    }

    @PostMapping("/addUser")
    public void addUser(@RequestBody UserInfo user) throws UsernameAlreadyExists, EmailAlreadyExists, InvalidPassword {
        userService.addUser(user);
    }

    @PreAuthorize("#username == authentication.principal.username")
    @PostMapping("/updateUser/{username}")
    public void updateUser(@PathVariable("username") String username, @RequestBody UserInfo user)
            throws UsernameAlreadyExists, EmailAlreadyExists, InvalidPassword {
        userService.updateUser(user);
    }

    @PreAuthorize("#username == authentication.principal.username")
    @PostMapping("/deleteUser/{username}")
    public void deleteUser(@PathVariable("username") String username, @RequestBody UserInfo user) {
        userService.deleteUser(user);
    }

}
