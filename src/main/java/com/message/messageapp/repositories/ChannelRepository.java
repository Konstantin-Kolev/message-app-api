package com.message.messageapp.repositories;

import com.message.messageapp.entities.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {
    @Query("SELECT c FROM Channel c WHERE c.isActive = 1")
    public List<Channel> findAllActive();
}
