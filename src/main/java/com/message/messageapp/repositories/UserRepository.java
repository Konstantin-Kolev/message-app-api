package com.message.messageapp.repositories;

import com.message.messageapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsernameAndPassword(String username, String password);

    @Query("SELECT u FROM User u WHERE u.isActive = 1")
    public List<User> findAllActive();

    public User findById(int id);
}
