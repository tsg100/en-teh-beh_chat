package de.fynnhenck.webApi.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.fynnhenck.webApi.utils.HelperFunctions;
import org.json.JSONObject;

import java.io.*;
import java.util.Scanner;

public class TestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {


        //System.out.println(exchange.getResponseBody().toString());

        //Scanner s = new Scanner(exchange.getRequestBody()).useDelimiter("\\A");
        //String result = s.hasNext() ? s.next() : "";


        System.out.println(HelperFunctions.getPostValue(exchange.getRequestBody(), "te"));
        //System.out.println(exchange.getRequestBody());



        JSONObject res = new JSONObject();
        res.put("Status", "OK");

        exchange.sendResponseHeaders(200, res.toString().getBytes().length);

        OutputStream out = exchange.getResponseBody();
        out.write(res.toString().getBytes());
        out.flush();
        exchange.close();

    }
}
