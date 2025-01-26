package com.message.messageapp.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "td_channels")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "channel_name")
    private String name;

    @Column(name = "channel_type")
    private String type;

    @Column(name = "is_active")
    private int isActive = 1;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany
    @JoinTable(name = "tc_channel_member",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> members;

    @ManyToMany
    @JoinTable(name = "tc_channel_admin",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> admins;

    @OneToMany(mappedBy = "channel")
    private List<Message> messages;
}
