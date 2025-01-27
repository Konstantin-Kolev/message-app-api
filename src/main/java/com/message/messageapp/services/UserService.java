package com.message.messageapp.services;

import com.message.messageapp.dto.UserCreateDto;
import com.message.messageapp.dto.UserOutputDto;
import com.message.messageapp.entities.User;
import com.message.messageapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        List<UserOutputDto> result = new ArrayList<>();
        for (var user : users) {
            UserOutputDto userOutputDto = new UserOutputDto();
            userOutputDto.setId(user.getId());
            userOutputDto.setUsername(user.getUsername());
            userOutputDto.setEmail(user.getEmail());
            result.add(userOutputDto);
        }

        return result;
    }

    public List<UserOutputDto> getAllUsersExceptCurrent(int userId) {
        var users = this.userRepository.findUsersNotInChannel(userId);
        List<UserOutputDto> result = new ArrayList<>();
        for (var user : users) {
            UserOutputDto userOutputDto = new UserOutputDto();
            userOutputDto.setId(user.getId());
            userOutputDto.setUsername(user.getUsername());
            userOutputDto.setEmail(user.getEmail());
            result.add(userOutputDto);
        }

        return result;
    }

    public List<UserOutputDto> getFriendsForUser(int userId) {
        var users = this.userRepository.findFriendsByUserId(userId);
        List<UserOutputDto> result = new ArrayList<>();
        for (var user : users) {
            UserOutputDto userOutputDto = new UserOutputDto();
            userOutputDto.setId(user.getId());
            userOutputDto.setUsername(user.getUsername());
            userOutputDto.setEmail(user.getEmail());
            result.add(userOutputDto);
        }

        return result;
    }

    public User create(UserCreateDto userInput) {
        User user = new User();
        user.setUsername(userInput.getUsername());
        user.setPassword(userInput.getPassword());
        user.setEmail(userInput.getEmail());
        return this.userRepository.save(user);
    }

    public boolean login(String email, String password) {
        var foundUser = this.userRepository.findByUsernameAndPassword(email, password);

        if (foundUser != null) {
            return true;
        }
        return false;
    }
}
