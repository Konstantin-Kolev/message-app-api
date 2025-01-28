package com.message.messageapp.repositories;

import com.message.messageapp.entities.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {
    @Query("SELECT c FROM Channel c WHERE c.isActive = 1")
    List<Channel> findAllActive();

    @Query("SELECT c FROM Channel c WHERE c.id = :id AND c.isActive = 1")
    Channel findById(int id);

    @Query("SELECT c FROM Channel c JOIN c.members m WHERE m.id = :userId")
    List<Channel> findChannelsByMemberId(int userId);

    @Query("SELECT COUNT(c) > 0 " +
            "FROM Channel c " +
            "WHERE c.type = 2 " +
            "AND (c.name = CONCAT(:username1, '|', :username2) OR c.name = CONCAT(:username2, '|', :username1))")
    boolean friendChannelExists(String username1, String username2);
}
