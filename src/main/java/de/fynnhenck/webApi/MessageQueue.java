package de.fynnhenck.webApi;

import de.fynnhenck.util.Chat;
import de.fynnhenck.util.Message;
import de.fynnhenck.util.User;

import java.util.ArrayList;

public class MessageQueue {

    User user;
    ArrayList<Chat> chats;
    boolean open;
    ArrayList<Chat> openChatsQueue; //also works as queue
    private int counter;


    public MessageQueue(User user){
        this.user = user;
        this.chats = user.getActiveChats();
    }

    public boolean isOpen() {
        return open;
    }

    public void openChats(){
        counter = 0;
        open = true;

        for(Chat chat : chats){
            //go over every chat an open the session in the browser
            chat.open();
            openChatsQueue.add(chat);

        }

    }

    public Message getNextMessage(){

        if(!openChatsQueue.get(counter).messageAvailable()){
            if(counter >= openChatsQueue.size()){
                counter = 0;
            }else {
                counter++;
            }
            getNextMessage(); //todo: see if that shit fucks me over
        }


        Message msg = openChatsQueue.get(counter).getMessage();
        msg.setCounter(counter); //set counter for rebinding/response
        //TODO: increase counter @ response
        return msg; //return msg when one is available

    }

    public void respond(String response, int counter){
        openChatsQueue.get(counter).respond(response);
        //TODO: check if res is > 120 zeichen oder whatever
        this.counter++;
    }


}
