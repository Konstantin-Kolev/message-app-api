package com.message.messageapp.repositories;

import com.message.messageapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsernameAndPassword(String username, String password);

    public List<User> findByIsActive(int isActive);
}
