package com.message.messageapp.services;

import com.message.messageapp.entities.User;
import com.message.messageapp.repositories.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User create(User user) {
        return this.userRepository.save(user);
    }
}
