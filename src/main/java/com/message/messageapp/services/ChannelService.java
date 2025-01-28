package com.message.messageapp.services;

import com.message.messageapp.dto.ChannelCreateDto;
import com.message.messageapp.dto.ChannelOutputDto;
import com.message.messageapp.dto.DtoConverter;
import com.message.messageapp.dto.UserOutputDto;
import com.message.messageapp.entities.Channel;
import com.message.messageapp.entities.User;
import com.message.messageapp.http.AppResponse;
import com.message.messageapp.repositories.ChannelRepository;
import com.message.messageapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    public ChannelService(ChannelRepository channelRepository, UserRepository userRepository) {
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
    }

    public List<ChannelOutputDto> getAllChannels() {
        var channels = this.channelRepository.findAllActive();
        return channels.stream().map(DtoConverter::convertChannelToOutputDto).collect(Collectors.toList());
    }

    public List<ChannelOutputDto> getChannelsByMember(int userId) {
        return this.channelRepository.findChannelsByMemberId(userId).stream().map(DtoConverter::convertChannelToOutputDto).collect(Collectors.toList());
    }

    public List<UserOutputDto> getChannelMembers(int channelId) throws Exception {
        Channel channel = this.channelRepository.findById(channelId);

        if (channel == null) {
            throw new Exception("Channel not found");
        }

        return channel.getMembers().stream().map(DtoConverter::convertUserToOutputDto).collect(Collectors.toList());
    }

    public List<UserOutputDto> getChannelAdmins(int channelId) throws Exception {
        Channel channel = this.channelRepository.findById(channelId);

        if (channel == null) {
            throw new Exception("Channel not found");
        }

        return channel.getAdmins().stream().map(DtoConverter::convertUserToOutputDto).collect(Collectors.toList());
    }

    public ChannelOutputDto createChannel(ChannelCreateDto channelData) throws Exception {

        if (channelData.getType() != 1 && channelData.getType() != 2) {
            throw new Exception("Invalid type of channel");
        }

        User owner = this.userRepository.findById(channelData.getOwnerId());

        if (owner == null) {
            throw new Exception("Owner doesn't exist in records");
        }

        Channel channel = new Channel();
        channel.setName(channelData.getName());
        channel.setType(channelData.getType());
        channel.setOwner(owner);

        channel.getMembers().add(owner);
        channel.getAdmins().add(owner);

        return DtoConverter.convertChannelToOutputDto(this.channelRepository.save(channel));
    }

    public ChannelOutputDto createFriendChannel(int userId, int friendId) throws Exception {
        User user = this.userRepository.findById(userId);
        User friend = this.userRepository.findById(friendId);

        if (user == null) {
            throw new Exception("User not found");
        }

        if (friend == null) {
            throw new Exception("Friend doesn't exist in records");
        }

        if (this.channelRepository.friendChannelExists(user.getUsername(), friend.getUsername())) {
            throw new Exception("Friend chat already exists");
        }

        Channel channel = new Channel();
        channel.setName(user.getUsername()+"|"+friend.getUsername());
        channel.setType(2);
        channel.setOwner(user);
        channel.getMembers().add(user);
        channel.getMembers().add(friend);
        channel.getAdmins().add(user);
        channel.getAdmins().add(friend);

        return DtoConverter.convertChannelToOutputDto(this.channelRepository.save(channel));
    }

    public ChannelOutputDto renameChannel(int channelId, String newName) throws Exception {
        Channel channel = this.channelRepository.findById(channelId);
        if (channel == null) {
            throw new Exception("Channel not found");
        }

        channel.setName(newName);
        return DtoConverter.convertChannelToOutputDto(this.channelRepository.save(channel));
    }

    public boolean deleteChannel(int channelId) throws Exception {
        Channel channel = this.channelRepository.findById(channelId);
        if (channel == null) {
            throw new Exception("Channel not found");
        }
        channel.setIsActive(0);
        this.channelRepository.save(channel);
        return true;
    }

    public ChannelOutputDto addMember(int channelId, int userId) throws Exception {
        Channel channel = this.channelRepository.findById(channelId);
        User user = this.userRepository.findById(userId);
        if (channel == null) {
            throw new Exception("Channel not found");
        }

        if (user == null) {
            throw new Exception("User not found");
        }

        if (!channel.getMembers().contains(user)) {
            channel.getMembers().add(user);
            return DtoConverter.convertChannelToOutputDto(this.channelRepository.save(channel));
        }

        throw new Exception("User is already member of channel");
    }

    public ChannelOutputDto removeMember(int channelId, int userId) throws Exception {
        Channel channel = this.channelRepository.findById(channelId);
        User user = this.userRepository.findById(userId);
        if (channel == null) {
            throw new Exception("Channel not found");
        }

        if (user == null) {
            throw new Exception("User not found");
        }

        if (channel.getMembers().contains(user)) {
            channel.getMembers().remove(user);
            return DtoConverter.convertChannelToOutputDto(this.channelRepository.save(channel));
        }

        throw new Exception("User is not member of channel");
    }

    public ChannelOutputDto addAdmin(int channelId, int userId) throws Exception {
        Channel channel = this.channelRepository.findById(channelId);
        User user = this.userRepository.findById(userId);
        if (channel == null) {
            throw new Exception("Channel not found");
        }

        if (user == null) {
            throw new Exception("User not found");
        }

        if (!channel.getAdmins().contains(user)) {
            channel.getAdmins().add(user);
            return DtoConverter.convertChannelToOutputDto(this.channelRepository.save(channel));
        }

        throw new Exception("User is already admin of channel");
    }

    public ChannelOutputDto removeAdmin(int channelId, int userId) throws Exception {
        Channel channel = this.channelRepository.findById(channelId);
        User user = this.userRepository.findById(userId);
        if (channel == null) {
            throw new Exception("Channel not found");
        }

        if (user == null) {
            throw new Exception("User not found");
        }

        if (channel.getAdmins().contains(user)) {
            channel.getAdmins().remove(user);
            return DtoConverter.convertChannelToOutputDto(this.channelRepository.save(channel));
        }

        throw new Exception("User is not admin of channel");
    }
}
