package com.message.messageapp.controllers;

import com.message.messageapp.entities.User;
import com.message.messageapp.http.AppResponse;
import com.message.messageapp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        var collection = this.userService.findAll();

        return AppResponse.success()
                .withMessage("All users in the system")
                .withData(collection)
                .build();
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        var response = this.userService.create(user);

        return AppResponse.success()
                .withMessage("New user created")
                .withData(response)
                .build();
    }
}
