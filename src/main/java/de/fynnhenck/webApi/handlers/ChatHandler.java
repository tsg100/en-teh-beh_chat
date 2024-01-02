package de.fynnhenck.webApi.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.fynnhenck.util.Message;
import de.fynnhenck.webApi.MessageQueue;
import de.fynnhenck.webApi.utils.HelperFunctions;
import de.fynnhenck.webApi.utils.UserSessionHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class ChatHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("GET")){

            Map<String, String> payload = HelperFunctions.queryToMap( exchange.getRequestURI().getQuery());

            int userId = Integer.parseInt(payload.get("user_id"));
            String authToken = payload.get("auth");

            //check user auth

            if(UserSessionHandler.getActiveUsers().get(userId).checkAuth(authToken)){
             //auth success
                //get Messages for user
                MessageQueue mq = new MessageQueue(UserSessionHandler.getActiveUsers().get(userId));
                UserSessionHandler.getActiveUsers().get(userId).setMq(mq);
                if(!mq.isOpen()){
                    mq.openChats();
                }

                Message msg = mq.getNextMessage(); //Display to user in to be made obj

                JSONObject res = new JSONObject();
                res.put("counter", msg.getCounter());
                res.put("messageContent", msg.getMessages());
                res.put("bio_c", msg.getBio_c());
                res.put("bio_f", msg.getBio_f());
                res.put("name_f", msg.getName_f());
                res.put("name_c", msg.getName_c());
                res.put("lockbook_c", msg.getLockbook_c());
                res.put("lockbook_f", msg.getLockbook_f());

                exchange.sendResponseHeaders(200, res.toString().getBytes().length);

                OutputStream out = exchange.getResponseBody();
                out.write(res.toString().getBytes());
                out.flush();
                exchange.close();


            }else{
                exchange.sendResponseHeaders(401, -1); //Unauthorized
            }
        }else{
            //wrong method
            exchange.sendResponseHeaders(405, -1);
        }
    }
}
