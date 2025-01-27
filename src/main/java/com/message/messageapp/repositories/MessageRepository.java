package com.message.messageapp.repositories;

import com.message.messageapp.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("SELECT m FROM Message m WHERE m.channel.id = :channelId AND m.isActive = 1 ORDER BY m.timestamp ASC")
    List<Message> findMessagesByChannelId(int channelId);
}
