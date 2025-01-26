package com.message.messageapp.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "td_channels")
@Getter()
@Setter()
@NoArgsConstructor()
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
    @JsonManagedReference
    private List<User> members;

    @ManyToMany
    @JoinTable(name = "tc_channel_admin",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonManagedReference
    private List<User> admins;

    @OneToMany(mappedBy = "channel")
    private List<Message> messages;
}
