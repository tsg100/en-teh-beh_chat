package de.fynnhenck.webApi.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.fynnhenck.webApi.utils.HelperFunctions;
import de.fynnhenck.webApi.utils.UserSessionHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class ResponseHandler implements HttpHandler {
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
                String counter = payload.get("counter");

                if(counter != null){
                    String response = HelperFunctions.getPostValue(exchange.getRequestBody(), "response"); //TODO: Response darf KEINE \n beinhalten!!!

                    //todo: length check

                    UserSessionHandler.getActiveUsers().get(userId).getMq().respond(response, Integer.parseInt(counter));

                    JSONObject res = new JSONObject();
                    res.put("Status", "OK");

                    exchange.sendResponseHeaders(200, res.toString().getBytes().length);

                    OutputStream out = exchange.getResponseBody();
                    out.write(res.toString().getBytes());
                    out.flush();
                    exchange.close();


                }else {
                    exchange.sendResponseHeaders(301, -1); //TODO: check if bad request code
                }
            }else{
                exchange.sendResponseHeaders(401, -1); //Unauthorized
            }
        }else{
            //wrong method
            exchange.sendResponseHeaders(405, -1);
        }
    }

}
