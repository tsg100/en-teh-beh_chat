package de.fynnhenck.webApi.utils;

import de.fynnhenck.util.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserSessionHandler {

    private static HashMap<Integer, User> activeUsers;

    public static void loginUser(User user){
        if(activeUsers.get(user.getId()) != null){
            activeUsers.remove(user.getId());
        }

        activeUsers.put(user.getId(), user);

    }

    public static HashMap<Integer, User> getActiveUsers(){
        return activeUsers;
    }

    public static void logoutUser(User user){
        activeUsers.remove(user.getId());
    }

}
