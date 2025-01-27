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
        List<MessageOutputDto> result = new ArrayList<>();
        for (Message message : messages) {
            result.add(DtoConverter.convertMessageToOutputDto(message));
        }

        return result;
    }

    public Message createMessage(MessageCreateDto createDto) {
        Channel channel = channelRepository.findById(createDto.getChannelId());
        User user = userRepository.findById(createDto.getSenderId());

        if (channel == null || user == null) {
            return null;
        }

        Message message = new Message();
        message.setChannel(channel);
        message.setSender(user);
        message.setContent(createDto.getContent());
        message.setTimestamp(createDto.getTimestamp());

        return messageRepository.save(message);
    }
}
