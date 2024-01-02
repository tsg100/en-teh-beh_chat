package de.fynnhenck.webApi;


import com.sun.net.httpserver.HttpServer;
import de.fynnhenck.webApi.handlers.ChatHandler;
import de.fynnhenck.webApi.handlers.LoginHandler;
import de.fynnhenck.webApi.handlers.ResponseHandler;
import de.fynnhenck.webApi.handlers.TestHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class ApiInterface {

    private HttpServer server;

    Logger logger = Logger.getLogger("ApiInterface");
    public ApiInterface() throws IOException {
        server = HttpServer.create(new InetSocketAddress(8080), 0);
        logger.info("Web Interface started! Creating Contexts");
        server.createContext("/api/login", new LoginHandler());

        server.createContext("/api/chat", new ChatHandler());

        server.createContext("/api/chat/respond", new ResponseHandler());

        server.createContext("/api/test", new TestHandler());

        server.setExecutor(null);
        server.start();
        logger.info("Server started successfully!");
    }


}
