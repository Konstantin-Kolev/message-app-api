package com.message.messageapp.services;

import com.message.messageapp.dto.DtoConverter;
import com.message.messageapp.dto.UserCreateDto;
import com.message.messageapp.dto.UserOutputDto;
import com.message.messageapp.entities.User;
import com.message.messageapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return this.userRepository.findAllActive();
    }

    public List<UserOutputDto> getUsersNotInChannel(int channelId) {
        var users =  this.userRepository.findUsersNotInChannel(channelId);

        return users.stream().map(DtoConverter::convertUserToOutputDto).collect(Collectors.toList());
    }

    public List<UserOutputDto> getAllUsersExceptCurrent(int userId) {
        var users = this.userRepository.findAllUsersExceptCurrent(userId);

        return users.stream().map(DtoConverter::convertUserToOutputDto).collect(Collectors.toList());
    }

    public List<UserOutputDto> getFriendsForUser(int userId) {
        var users = this.userRepository.findFriendsByUserId(userId);

        return users.stream().map(DtoConverter::convertUserToOutputDto).collect(Collectors.toList());
    }

    public User create(UserCreateDto userInput) throws Exception {
        User usernameCheck = this.userRepository.findByUsername(userInput.getUsername());
        User emailCheck = this.userRepository.findByEmail(userInput.getEmail());

        if (usernameCheck != null) {
            throw new Exception("Username is taken");
        }

        if (emailCheck != null) {
            throw new Exception("Email is taken");
        }


        User user = new User();
        user.setUsername(userInput.getUsername());
        user.setPassword(userInput.getPassword());
        user.setEmail(userInput.getEmail());
        return this.userRepository.save(user);
    }

    public boolean login(String email, String password) {
        var foundUser = this.userRepository.findByEmailAndPassword(email, password);

        return foundUser != null;
    }
}
