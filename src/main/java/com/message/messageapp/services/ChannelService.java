package com.message.messageapp.services;

import com.message.messageapp.dto.ChannelCreateDto;
import com.message.messageapp.dto.ChannelOutputDto;
import com.message.messageapp.dto.DtoConverter;
import com.message.messageapp.dto.UserOutputDto;
import com.message.messageapp.entities.Channel;
import com.message.messageapp.entities.User;
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

    public List<Channel> getChannelsByMember(int userId) {
    return this.channelRepository.findChannelsByMemberId(userId);
    }

    public List<UserOutputDto> getChannelMembers(int channelId) {
        Channel channel = this.channelRepository.findById(channelId);

        if (channel == null) {
            return null;
        }

        return channel.getMembers().stream().map(DtoConverter::convertUserToOutputDto).collect(Collectors.toList());
    }

    public List<UserOutputDto> getChannelAdmins(int channelId) {
        Channel channel = this.channelRepository.findById(channelId);

        if (channel == null) {
            return null;
        }

        return channel.getAdmins().stream().map(DtoConverter::convertUserToOutputDto).collect(Collectors.toList());
    }

    public Channel createChannel(ChannelCreateDto channelData) {
        User owner = this.userRepository.findById(channelData.getOwnerId());

        if (owner == null) {
            return null;
        }

        Channel channel = new Channel();
        channel.setName(channelData.getName());
        channel.setType(channelData.getType());
        channel.setOwner(owner);

        channel.getMembers().add(owner);
        channel.getAdmins().add(owner);

        return this.channelRepository.save(channel);
    }

    public Channel renameChannel(int channelId, String newName) {
        Channel channel = this.channelRepository.findById(channelId);
        if (channel == null) {
            return null;
        }

        channel.setName(newName);
        return this.channelRepository.save(channel);
    }

    public boolean deleteChannel(int channelId) {
        Channel channel = this.channelRepository.findById(channelId);
        if (channel == null) {
            return false;
        }
        channel.setIsActive(0);
        this.channelRepository.save(channel);
        return true;
    }

    public Channel addMember(int channelId, int userId) {
        Channel channel = this.channelRepository.findById(channelId);
        User user = this.userRepository.findById(userId);
        if (channel == null || user == null) {
            return null;
        }

        if(!channel.getMembers().contains(user)) {
            channel.getMembers().add(user);
            return this.channelRepository.save(channel);
        }

        return channel;
    }

    public Channel removeMember(int channelId, int userId) {
        Channel channel = this.channelRepository.findById(channelId);
        User user = this.userRepository.findById(userId);
        if (channel == null || user == null) {
            return null;
        }

        if(channel.getMembers().contains(user)) {
            channel.getMembers().remove(user);
            return this.channelRepository.save(channel);
        }

        return channel;
    }

    public Channel addAdmin(int channelId, int userId) {
        Channel channel = this.channelRepository.findById(channelId);
        User user = this.userRepository.findById(userId);
        if (channel == null || user == null) {
            return null;
        }

        if(!channel.getAdmins().contains(user)) {
            channel.getAdmins().add(user);
            return this.channelRepository.save(channel);
        }

        return channel;
    }

    public Channel removeAdmin(int channelId, int userId) {
        Channel channel = this.channelRepository.findById(channelId);
        User user = this.userRepository.findById(userId);
        if (channel == null || user == null) {
            return null;
        }

        if(channel.getAdmins().contains(user)) {
            channel.getAdmins().remove(user);
            return this.channelRepository.save(channel);
        }

        return channel;
    }
}
