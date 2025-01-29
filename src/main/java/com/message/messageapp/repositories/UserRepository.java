package com.message.messageapp.repositories;

import com.message.messageapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password AND u.isActive = 1")
    User findByEmailAndPassword(String email, String password);

    @Query("SELECT u FROM User u WHERE u.isActive = 1")
    List<User> findAllActive();

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.isActive = 1")
    User findById(int id);

    @Query("SELECT u FROM User u WHERE u.id NOT IN (SELECT m.id FROM Channel c JOIN c.members m WHERE c.id = :channelId) AND u.isActive = 1")
    List<User> findUsersNotInChannel(int channelId);

    @Query("SELECT u FROM User u WHERE u.id != :userId AND u.isActive = 1")
    List<User> findAllUsersExceptCurrent(int userId);

    @Query("SELECT u FROM User u " +
            "WHERE u.id IN (" +
            "   SELECT m.id FROM Channel c " +
            "   JOIN c.members m " +
            "   WHERE c.type = 2 " +
            "   AND c.id IN (SELECT c2.id FROM Channel c2 JOIN c2.members m2 WHERE m2.id = :userId) " +
            "   AND m.id <> :userId" +
            ")")
    List<User> findFriendsByUserId(int userId);

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.isActive = 1")
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.isActive = 1")
    User findByEmail(String email);
}
