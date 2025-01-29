package com.message.messageapp.controllers;

import com.message.messageapp.dto.ChannelCreateDto;
import com.message.messageapp.http.AppResponse;
import com.message.messageapp.services.ChannelService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
        try {
            var collection = this.channelService.getChannelMembers(channelId);

            return AppResponse.success()
                    .withMessage("Channel members")
                    .withData(collection)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withMessage(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/{channelId}/admins")
    public ResponseEntity<?> getAllAdmins(@PathVariable int channelId) {
        try {
            var collection = this.channelService.getChannelAdmins(channelId);

            return AppResponse.success()
                    .withMessage("Channel admins")
                    .withData(collection)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withMessage(e.getMessage())
                    .build();
        }
    }

    @PostMapping()
    public ResponseEntity<?> createChannel(@RequestBody ChannelCreateDto createDto) {
        try {
            var channel = this.channelService.createChannel(createDto);

            return AppResponse.success()
                    .withMessage("Channel created")
                    .withData(channel)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withMessage(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/friend")
    public ResponseEntity<?> createFriendChannel(@RequestBody Map<String, Object> input) {
        int userId = (int) input.get("userId");
        int friendId = (int) input.get("friendId");

        try {
            var channel = this.channelService.createFriendChannel(userId, friendId);

            return AppResponse.success()
                    .withMessage("Channel created")
                    .withData(channel)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withMessage(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/rename/{channelId}")
    public ResponseEntity<?> renameChannel(@PathVariable int channelId, @RequestParam String newName) {
        try {
            var channel = this.channelService.renameChannel(channelId, newName);

            return AppResponse.success()
                    .withMessage("Channel renamed")
                    .withData(channel)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withMessage(e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/{channelId}")
    public ResponseEntity<?> deleteChannel(@PathVariable int channelId) {
        try {
            this.channelService.deleteChannel(channelId);

            return AppResponse.success()
                    .withMessage("Channel deleted")
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withMessage(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/{channelId}/addMember/{userId}")
    public ResponseEntity<?> addMember(@PathVariable int channelId, @PathVariable int userId) {
        try {
            var channel = this.channelService.addMember(channelId, userId);

            return AppResponse.success()
                    .withMessage("Member added to channel")
                    .withData(channel)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withMessage(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/{channelId}/removeMember/{userId}")
    public ResponseEntity<?> removeMember(@PathVariable int channelId, @PathVariable int userId) {
        try {
            var channel = this.channelService.removeMember(channelId, userId);

            return AppResponse.success()
                    .withMessage("Member removed to channel")
                    .withData(channel)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withMessage(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/{channelId}/addAdmin/{userId}")
    public ResponseEntity<?> addAdmin(@PathVariable int channelId, @PathVariable int userId) {
        try {
            var channel = this.channelService.addAdmin(channelId, userId);

            return AppResponse.success()
                    .withMessage("Admin added to channel")
                    .withData(channel)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withMessage(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/{channelId}/removeAdmin/{userId}")
    public ResponseEntity<?> removeAdmin(@PathVariable int channelId, @PathVariable int userId) {
        try {
            var channel = this.channelService.removeAdmin(channelId, userId);

            return AppResponse.success()
                    .withMessage("Admin removed to channel")
                    .withData(channel)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withMessage(e.getMessage())
                    .build();
        }
    }
}
