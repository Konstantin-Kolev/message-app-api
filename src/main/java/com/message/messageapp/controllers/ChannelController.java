package com.message.messageapp.controllers;

import com.message.messageapp.dto.ChannelCreateDto;
import com.message.messageapp.http.AppResponse;
import com.message.messageapp.services.ChannelService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/channels")
public class ChannelController {

    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllChannels() {
        var collection = this.channelService.getAllChannels();

        return AppResponse.success()
                .withMessage("All channels in the system")
                .withData(collection)
                .build();
    }

    @GetMapping("/byMember/{memberId}")
    public ResponseEntity<?> getChannelByMemberId(@PathVariable int memberId) {
        var collection = this.channelService.getChannelsByMember(memberId);

        return AppResponse.success()
                .withMessage("Channels the user is a member of")
                .withData(collection)
                .build();
    }

    @GetMapping("/{channelId}/members")
    public ResponseEntity<?> getAllMembers(@PathVariable int channelId) {
        var collection = this.channelService.getChannelMembers(channelId);

        if (collection == null) {
            return AppResponse.error()
                    .withMessage("Channel " + channelId + " doesn't exist")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Channel members")
                .withData(collection)
                .build();
    }

    @GetMapping("/{channelId}/admins")
    public ResponseEntity<?> getAllAdmins(@PathVariable int channelId) {
        var collection = this.channelService.getChannelAdmins(channelId);

        if (collection == null) {
            return AppResponse.error()
                    .withMessage("Channel " + channelId + " doesn't exist")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Channel admins")
                .withData(collection)
                .build();
    }

    @PostMapping()
    public ResponseEntity<?> createChannel(@RequestBody ChannelCreateDto createDto) {

        if (createDto.getType() != 1 && createDto.getType() != 2) {
            return AppResponse.error()
                    .withMessage("Invalid type of channel")
                    .build();
        }

        var channel = this.channelService.createChannel(createDto);
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

    @PostMapping("/rename/{channelId}")
    public ResponseEntity<?> renameChannel(@PathVariable int channelId, @RequestParam String newName) {
        var channel = this.channelService.renameChannel(channelId, newName);
        if (channel == null) {
            return AppResponse.error()
                    .withMessage("Channel " + channelId + " doesn't exist")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Channel renamed")
                .withData(channel)
                .build();
    }

    @DeleteMapping("/{channelId}")
    public ResponseEntity<?> deleteChannel(@PathVariable int channelId) {
        var result = this.channelService.deleteChannel(channelId);
        if (result) {
            return AppResponse.error()
                    .withMessage("Channel " + channelId + " doesn't exist")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Channel deleted")
                .build();
    }

    @PostMapping("/{channelId}/addMember/{userId}")
    public ResponseEntity<?> addMember(@PathVariable int channelId, @PathVariable int userId) {
        var channel = this.channelService.addMember(channelId, userId);

        if (channel == null) {
            return AppResponse.error()
                    .withMessage("Channel or User doesn't exist")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Member added to channel")
                .withData(channel)
                .build();
    }

    @PostMapping("/{channelId}/removeMember/{userId}")
    public ResponseEntity<?> removeMember(@PathVariable int channelId, @PathVariable int userId) {
        var channel = this.channelService.removeMember(channelId, userId);
        if (channel == null) {
            return AppResponse.error()
                    .withMessage("Channel or User doesn't exist")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Member removed to channel")
                .withData(channel)
                .build();
    }

    @PostMapping("/{channelId}/addAdmin/{userId}")
    public ResponseEntity<?> addAdmin(@PathVariable int channelId, @PathVariable int userId) {
        var channel = this.channelService.addAdmin(channelId, userId);
        if (channel == null) {
            return AppResponse.error()
                    .withMessage("Channel or User doesn't exist")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Admin added to channel")
                .withData(channel)
                .build();


    }

    @PostMapping("/{channelId}/removeAdmin/{userId}")
    public ResponseEntity<?> removeAdmin(@PathVariable int channelId, @PathVariable int userId) {
        var channel = this.channelService.removeAdmin(channelId, userId);
        if (channel == null) {
            return AppResponse.error()
                    .withMessage("Channel or User doesn't exist")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Admin removed to channel")
                .withData(channel)
                .build();


    }
}
