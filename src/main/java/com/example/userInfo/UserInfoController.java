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

@RestController
@RequestMapping("/user")
public class UserInfoController {

    private final UserInfoService userService;

    @Autowired
    public UserInfoController(UserInfoService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUsername/{username}")
    @PreAuthorize("#username == authentication.principal.username and hasRole('ROLE_CUSTOMER')")
    public Optional<UserInfo> getUser(@PathVariable("username") String username) {
        return userService.getUser(username);
    }

    @PostMapping("/addUser")
    public void addUser(@RequestBody UserInfo user) {
        userService.addUser(user);
    }

    @PreAuthorize("#username == authentication.principal.username")
    @PostMapping("/updateUser")
    public void updateUser(@RequestBody UserInfo user) {
        userService.updateUser(user);
    }

    @PreAuthorize("#username == authentication.principal.username")
    @PostMapping("/deleteUser")
    public void deleteUser(@RequestBody UserInfo user) {
        userService.deleteUser(user);
    }

}
