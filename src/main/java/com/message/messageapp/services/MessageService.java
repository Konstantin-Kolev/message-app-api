package com.message.messageapp.services;

import com.message.messageapp.dto.DtoConverter;
import com.message.messageapp.dto.MessageCreateDto;
import com.message.messageapp.dto.MessageOutputDto;
import com.message.messageapp.entities.Channel;
import com.message.messageapp.entities.Message;
import com.message.messageapp.entities.User;
import com.message.messageapp.repositories.ChannelRepository;
import com.message.messageapp.repositories.MessageRepository;
import com.message.messageapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository, ChannelRepository channelRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
    }

    public List<MessageOutputDto> getMessagesForChannel(int channelId) {
        var messages = this.messageRepository.findMessagesByChannelId(channelId);

        return messages.stream().map(DtoConverter::convertMessageToOutputDto).collect(Collectors.toList());
    }

    public List<MessageOutputDto> getMessagesBetweenFriend(int userId, int friendId) {
        var messages = this.messageRepository.findMessagesBetweenFriends(userId, friendId);

        return messages.stream().map(DtoConverter::convertMessageToOutputDto).collect(Collectors.toList());
    }

    public Message createMessage(MessageCreateDto createDto) throws Exception {
        Channel channel = channelRepository.findById(createDto.getChannelId());
        User user = userRepository.findById(createDto.getSenderId());

        if (channel == null) {
            throw new Exception("Channel not found");
        }

        if (user == null) {
            throw new Exception("User not found");
        }

        if (!channel.getMembers().contains(user)) {
            throw new Exception("User is not a member of the chanel");
        }

        Message message = new Message();
        message.setChannel(channel);
        message.setSender(user);
        message.setContent(createDto.getContent());
        message.setTimestamp(createDto.getTimestamp());

        return messageRepository.save(message);
    }
}
