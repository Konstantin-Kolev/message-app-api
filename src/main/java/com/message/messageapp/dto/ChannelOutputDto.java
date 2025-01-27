package com.message.messageapp.dto;

import java.util.List;

public class ChannelOutputDto {

    private int id;
    private String name;
    private int type;
    private UserOutputDto owner;
    private List<UserOutputDto> members;
    private List<UserOutputDto> admins;

    public ChannelOutputDto() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public UserOutputDto getOwner() {
        return owner;
    }

    public void setOwner(UserOutputDto owner) {
        this.owner = owner;
    }

    public List<UserOutputDto> getMembers() {
        return members;
    }

    public void setMembers(List<UserOutputDto> members) {
        this.members = members;
    }

    public List<UserOutputDto> getAdmins() {
        return admins;
    }

    public void setAdmins(List<UserOutputDto> admins) {
        this.admins = admins;
    }
}
