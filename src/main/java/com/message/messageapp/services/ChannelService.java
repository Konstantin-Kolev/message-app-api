package com.message.messageapp.services;

import com.message.messageapp.dto.ChannelCreateRequest;
import com.message.messageapp.dto.UserOutputDto;
import com.message.messageapp.entities.Channel;
import com.message.messageapp.entities.User;
import com.message.messageapp.repositories.ChannelRepository;
import com.message.messageapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelService {

    private ChannelRepository channelRepository;
    private UserRepository userRepository;

    public ChannelService(ChannelRepository channelRepository, UserRepository userRepository) {
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
    }


    public List<Channel> getAllChannels() {
        return this.channelRepository.findAllActive();
    }

    public List<Channel> getChannelsByMember(int userId) {
    return this.channelRepository.findChannelsByMemberId(userId);
    }

    public List<UserOutputDto> getChannelMembers(int channelId) {
        Channel channel = this.channelRepository.findById(channelId);

        if (channel == null) {
            return null;
        }

        List<UserOutputDto> result = new ArrayList<>();

        for (User member : channel.getMembers()) {
            UserOutputDto outputDto =  new UserOutputDto();
            outputDto.setId(member.getId());
            outputDto.setUsername(member.getUsername());
            outputDto.setEmail(member.getEmail());
            result.add(outputDto);
        }

        return result;
    }

    public List<UserOutputDto> getChannelAdmins(int channelId) {
        Channel channel = this.channelRepository.findById(channelId);

        if (channel == null) {
            return null;
        }

        List<UserOutputDto> result = new ArrayList<>();

        for (User member : channel.getAdmins()) {
            UserOutputDto outputDto =  new UserOutputDto();
            outputDto.setId(member.getId());
            outputDto.setUsername(member.getUsername());
            outputDto.setEmail(member.getEmail());
            result.add(outputDto);
        }

        return result;
    }

    public Channel createChannel(ChannelCreateRequest channelData) {
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
