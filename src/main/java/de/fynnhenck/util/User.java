package de.fynnhenck.util;

import de.fynnhenck.webApi.MessageQueue;

import java.util.ArrayList;
import java.util.UUID;

public class User {

    int id;
    String username;
    int role; //1 - user; 2 admin
    ArrayList<Chat> activeChats;
    String auth; //will be used as auth header; only valid 4 session
    MessageQueue mq;


    public User(int id, String username, int role){
        this.id = id;
        this.username = username;
        this.role = role;
        auth = UUID.randomUUID().toString();
    }

    public void setMq(MessageQueue mq) {
        this.mq = mq;
    }

    public MessageQueue getMq() {
        return mq;
    }

    public int getId() {
        return id;
    }

    public int getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Chat> getActiveChats() {
        return activeChats;
    }

    public void addActiveChat(Chat chat){
        activeChats.add(chat);
    }


    public String getAuth() {
        return auth;
    }

    public boolean checkAuth(String auth){
        return this.auth.equals(auth);
    }
}
