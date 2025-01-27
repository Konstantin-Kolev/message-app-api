package com.message.messageapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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
    @JsonBackReference("user-channel-member")
//    @JsonIgnoreProperties("members")
    private List<Channel> memberChannels = new ArrayList<>();

    @ManyToMany(mappedBy = "admins")
    @JsonBackReference("user-channel-admin")
//    @JsonIgnoreProperties("admins")
    private List<Channel> adminChannels = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<Message> messages;

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public List<Channel> getMemberChannels() {
        return memberChannels;
    }

    public void setMemberChannels(List<Channel> memberChannels) {
        this.memberChannels = memberChannels;
    }

    public List<Channel> getAdminChannels() {
        return adminChannels;
    }

    public void setAdminChannels(List<Channel> adminChannels) {
        this.adminChannels = adminChannels;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
