package com.example.userInfo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserInfoController {

    private final UserInfoService userService;

    @Autowired
    public UserInfoController(UserInfoService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUser/{userId}")
    public Optional<UserInfo> getUser(@PathVariable("userId") Integer id) {
        return userService.getUser(id);
    }

    @PostMapping("/addUser")
    public void addUser(@RequestBody UserInfo user) {
        userService.addUser(user);
    }

}
