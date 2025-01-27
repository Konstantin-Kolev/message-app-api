package com.message.messageapp.services;

import com.message.messageapp.dto.ChannelCreateRequest;
import com.message.messageapp.entities.Channel;
import com.message.messageapp.entities.User;
import com.message.messageapp.repositories.ChannelRepository;
import com.message.messageapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {

    private ChannelRepository channelRepository;
    private UserRepository userRepository;

    public ChannelService(ChannelRepository channelRepository, UserRepository userRepository) {
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
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

    public List<Channel> getAllChannels() {
        return this.channelRepository.findAllActive();
    }
}
