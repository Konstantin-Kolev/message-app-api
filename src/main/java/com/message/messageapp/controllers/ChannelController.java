package com.message.messageapp.controllers;

import com.message.messageapp.dto.ChannelCreateRequest;
import com.message.messageapp.http.AppResponse;
import com.message.messageapp.services.ChannelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ChannelController {

    private ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping("/channels")
    public ResponseEntity<?> getAllChannels() {
        var collection = this.channelService.getAllChannels();

        return AppResponse.success()
                .withMessage("All channels in the system")
                .withData(collection)
                .build();
    }

    @PostMapping("/channels")
    public ResponseEntity<?> createChannel(@RequestBody ChannelCreateRequest request) {

        if (request.getType() != 1 && request.getType() != 2) {
            return AppResponse.error()
                    .withMessage("Invalid type of channel")
                    .build();
        }

        var channel = this.channelService.createChannel(request);
        if (channel == null) {
            return AppResponse.error()
                    .withMessage("Owner doesn't exist")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Channel created")
                .withData(channel)
                .build();
    }
}
