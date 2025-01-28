package com.message.messageapp.controllers;

import com.message.messageapp.dto.DtoConverter;
import com.message.messageapp.dto.UserCreateDto;
import com.message.messageapp.dto.UserOutputDto;
import com.message.messageapp.http.AppResponse;
import com.message.messageapp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllUsers() {
        var collection = this.userService.getAll();

        return AppResponse.success()
                .withMessage("All users in the system")
                .withData(collection)
                .build();
    }

    @GetMapping("/notInChannel/{channelId}")
    public ResponseEntity<?> getUserNotInChannel(@PathVariable int channelId) {
        var collection = this.userService.getUsersNotInChannel(channelId);

        return AppResponse.success()
                .withMessage("Users not in channel")
                .withData(collection)
                .build();
    }

    @GetMapping("/exceptUser/{userId}")
    public ResponseEntity<?> getUsersExceptCurrent(@PathVariable int userId) {
        var collection = this.userService.getAllUsersExceptCurrent(userId);

        return AppResponse.success()
                .withMessage("Users for friends")
                .withData(collection)
                .build();
    }

    @GetMapping("/{userId}/friends")
    public ResponseEntity<?> getFriendsForUser(@PathVariable int userId) {
        var collection = this.userService.getFriendsForUser(userId);

        return AppResponse.success()
                .withMessage("Friends for user")
                .withData(collection)
                .build();
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody UserCreateDto userInput) {
        try {
            var response = this.userService.create(userInput);
            UserOutputDto outputDto = DtoConverter.convertUserToOutputDto(response);

            return AppResponse.success()
                    .withMessage("New user created")
                    .withData(outputDto)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withMessage(e.getMessage())
                    .build();
        }
    }
}
