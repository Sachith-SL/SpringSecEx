package com.sachith.SpringSecEx.controller;

import com.sachith.SpringSecEx.model.User;
import com.sachith.SpringSecEx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user){
        user.setUserName(user.getUserName().toLowerCase());
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        user.setUserName(user.getUserName().toLowerCase());
        return userService.verify(user);
    }
}
