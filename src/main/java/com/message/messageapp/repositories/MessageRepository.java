package com.message.messageapp.repositories;

import com.message.messageapp.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("SELECT m FROM Message m WHERE m.channel.id = :channelId AND m.isActive = 1 ORDER BY m.timestamp ASC")
    List<Message> findMessagesByChannelId(int channelId);

    @Query("SELECT m FROM Message m " +
            "WHERE m.channel.type = 2 " +
            "AND m.channel.id IN (" +
            "    SELECT c.id FROM Channel c " +
            "    JOIN c.members u " +
            "    WHERE c.type = 2 " +
            "    AND :userId IN (SELECT u1.id FROM c.members u1) " +
            "    AND :friendId IN (SELECT u2.id FROM c.members u2)" +
            ") " +
            "AND m.isActive = 1 " +
            "ORDER BY m.timestamp ASC")
    List<Message> findMessagesBetweenFriends(int userId, int friendId);
}
