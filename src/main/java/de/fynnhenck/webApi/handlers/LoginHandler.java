package de.fynnhenck.webApi.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.fynnhenck.database.DatabaseConnection;
import de.fynnhenck.util.Chat;
import de.fynnhenck.util.User;
import de.fynnhenck.webApi.utils.HelperFunctions;
import de.fynnhenck.webApi.utils.UserSessionHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if(exchange.getRequestMethod().equals("GET")){
            //resp is valid
            Map<String, String> payload = HelperFunctions.queryToMap( exchange.getRequestURI().getQuery());

            String username = payload.get("username");
            String pass = payload.get("password");

            DatabaseConnection db = new DatabaseConnection();
            User user = db.loginUser(username, pass);

            if(user != null){
                //user is authenticated
                JSONObject res = new JSONObject();
                //return Sessionkey to user
                res.put("SessionKey", user.getAuth());
                res.put("UserId", user.getId());
                res.put("Username", user.getUsername());
                exchange.sendResponseHeaders(200, res.toString().getBytes().length);
                //push auth to user

                UserSessionHandler.loginUser(user);
                //User session is now stored in hash map

                OutputStream out = exchange.getResponseBody();
                out.write(res.toString().getBytes());
                out.flush();
                exchange.close();


                //check which chats the user is active in
                List<Chat> chats = db.getActiveChats(user);

                for(Chat chat : chats){
                    user.addActiveChat(chat);
                }

                //start up chat clients for logged on users

            }else{
                //wrong/inactive creds
                exchange.sendResponseHeaders(401, -1);
            }

        }else{
            //wrong method
            exchange.sendResponseHeaders(405, -1);
        }

    }
}
