package com.message.messageapp.dto;

import com.message.messageapp.entities.Channel;
import com.message.messageapp.entities.Message;
import com.message.messageapp.entities.User;

import java.util.stream.Collectors;

public class DtoConverter {

    public static UserOutputDto convertUserToOutputDto(User user) {
        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setId(user.getId());
        userOutputDto.setUsername(user.getUsername());
        userOutputDto.setEmail(user.getEmail());

        return userOutputDto;
    }

    public static MessageOutputDto convertMessageToOutputDto(Message message) {
        MessageOutputDto messageOutputDto = new MessageOutputDto();
        messageOutputDto.setId(message.getId());
        messageOutputDto.setChannel(message.getChannel().getName());
        messageOutputDto.setSender(message.getSender().getUsername());
        messageOutputDto.setContent(message.getContent());
        messageOutputDto.setTimestamp(message.getTimestamp());

        return messageOutputDto;
    }

    public static ChannelOutputDto convertChannelToOutputDto(Channel channel) {
        ChannelOutputDto channelOutputDto = new ChannelOutputDto();
        channelOutputDto.setId(channel.getId());
        channelOutputDto.setName(channel.getName());
        channelOutputDto.setType(channel.getType());
        channelOutputDto.setOwner(DtoConverter.convertUserToOutputDto(channel.getOwner()));
        channelOutputDto.setMembers(channel.getMembers().stream().map(DtoConverter::convertUserToOutputDto).collect(Collectors.toList()));
        channelOutputDto.setAdmins(channel.getAdmins().stream().map(DtoConverter::convertUserToOutputDto).collect(Collectors.toList()));

        return channelOutputDto;
    }
}
