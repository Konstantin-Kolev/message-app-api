package com.message.messageapp.controllers;

import com.message.messageapp.dto.MessageCreateDto;
import com.message.messageapp.http.AppResponse;
import com.message.messageapp.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/fromChannel/{channelId}")
    public ResponseEntity<?> getMessagesForChannel(@PathVariable int channelId) {
        var collection = messageService.getMessagesForChannel(channelId);

        return AppResponse.success()
                .withMessage("Messages for channel")
                .withData(collection)
                .build();
    }

    @GetMapping("/{userId}/withFriend/{friendId}")
    public ResponseEntity<?> getMessagesBetweenFriends(@PathVariable int userId, @PathVariable int friendId) {
        var collection = this.messageService.getMessagesBetweenFriend(userId, friendId);

        return AppResponse.success()
                .withMessage("Messages between friends")
                .withData(collection)
                .build();
    }

    @PostMapping()
    public ResponseEntity<?> createMessage(@RequestBody MessageCreateDto createDto) {
        try {
        var result = messageService.createMessage(createDto);

        if (result == null) {
            return AppResponse.error()
                    .withMessage("Channel or User doesn't exist")
                    .build();
        }

        return AppResponse.success()
                .withData("Message created")
                .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withMessage(e.getMessage())
                    .build();
        }
    }
}
