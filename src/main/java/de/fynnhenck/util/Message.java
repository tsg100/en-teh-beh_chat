package de.fynnhenck.util;

public class Message {

    String messages;
    int counter; //to rebind to right open message in MessageQueue
    String lockbook_c; //customer
    String lockbook_f; //fake
    String name_f;
    String name_c;
    String bio_f;
    String bio_c;

    public Message(String messages){
        this.messages = messages;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getBio_c() {
        return bio_c;
    }

    public String getBio_f() {
        return bio_f;
    }

    public String getLockbook_c() {
        return lockbook_c;
    }

    public String getMessages() {
        return messages;
    }

    public String getLockbook_f() {
        return lockbook_f;
    }

    public String getName_c() {
        return name_c;
    }

    public String getName_f() {
        return name_f;
    }

    public void setBio_c(String bio_c) {
        this.bio_c = bio_c;
    }

    public void setBio_f(String bio_f) {
        this.bio_f = bio_f;
    }

    public void setLockbook_c(String lockbook_c) {
        this.lockbook_c = lockbook_c;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public void setLockbook_f(String lockbook_f) {
        this.lockbook_f = lockbook_f;
    }

    public void setName_c(String name_c) {
        this.name_c = name_c;
    }

    public void setName_f(String name_f) {
        this.name_f = name_f;
    }
}
