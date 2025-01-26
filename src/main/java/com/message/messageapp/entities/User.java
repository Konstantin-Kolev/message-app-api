package com.message.messageapp.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "td_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private int isActive = 1;

    @ManyToMany(mappedBy = "members")
    private List<Channel> memberChannels;

    @ManyToMany(mappedBy = "admins")
    private List<Channel> adminChannels;

    @OneToMany(mappedBy = "sender")
    private List<Message> messages;
}
