package com.message.messageapp.services;

import com.message.messageapp.entities.User;
import com.message.messageapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return this.userRepository.findByIsActive(1);
    }

    public User create(User user) {
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
