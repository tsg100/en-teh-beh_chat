package de.fynnhenck.util;

public class Chat {

    int chatId;
    String chatUser;
    String chatPassword;
    boolean open;

    public Chat(int chatId, String user, String pass){
        this.chatId = chatId;
        this.chatPassword = pass;
        this.chatUser = user;
        this.open = false;
    }

    public String getChatUser(){
        return chatUser;
    }
    public void open(){}

    public void checkForMessage(){}

    public Message getMessage(){return null;}

    public void respond(String response){}

    public String getChatPassword() {
        return chatPassword;
    }

    public boolean messageAvailable(){return false;};

    public int getChatId() {
        return chatId;
    }

    public void setOpen(boolean open){
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }
}
