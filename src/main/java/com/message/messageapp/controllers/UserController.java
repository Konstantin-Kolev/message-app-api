package com.message.messageapp.controllers;

import com.message.messageapp.dto.UserCreateDto;
import com.message.messageapp.dto.UserOutputDto;
import com.message.messageapp.entities.User;
import com.message.messageapp.http.AppResponse;
import com.message.messageapp.services.UserService;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "/users")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDto userInput) {
        var response = this.userService.create(userInput);
        UserOutputDto outputDto = new UserOutputDto();
        outputDto.setId(response.getId());
        outputDto.setUsername(response.getUsername());
        outputDto.setEmail(response.getEmail());

        return AppResponse.success()
                .withMessage("New user created")
                .withData(outputDto)
                .build();
    }
}
