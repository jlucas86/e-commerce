package com.example.userInfo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/getUser/{userId}")
    public Optional<UserInfo> getUser(@PathVariable("userId") Integer id) {
        return userService.getUser(id);
    }

    @GetMapping("/getUsername/{username}")
    public Optional<UserInfo> getUser(@PathVariable("username") String username) {
        return userService.getUser(username);
    }

    @GetMapping("/getAllUser")
    public List<UserInfo> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping("/addUser")
    public void addUser(@RequestBody UserInfo user) {
        System.out.println("controller entered!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        userService.addUser(user);
    }

}
