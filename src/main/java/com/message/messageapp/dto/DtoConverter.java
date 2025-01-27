package com.message.messageapp.dto;

import com.message.messageapp.entities.Message;
import com.message.messageapp.entities.User;

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
}
