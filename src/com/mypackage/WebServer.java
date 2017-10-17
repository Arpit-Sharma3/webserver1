package com.mypackage;

import org.apache.log4j.Logger;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * webserver connect to new socket and calling HTTpHandler's method process()
 */
public class WebServer extends Thread {
    public Integer DEFAULT_PORT;

    public WebServer(String DEFAULT_PORT) {
        this.DEFAULT_PORT = Integer.parseInt(DEFAULT_PORT);
    }

    final static Logger LOGGER = Logger.getLogger(WebServer.class.getName());

    /**
     * running thread using overrides run method
     */
    public void server() {
        try {
            /* define a host server  */
            ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);
            LOGGER.info("server socket started at PORT " + serverSocket.getLocalPort() + " and waiting for client request");
            System.out.println("Web Server started at PORT:\'" + serverSocket.getLocalPort() + "\' and waiting for client request...");
            Socket connection;
            while (true) {
                /* establish connection */
                connection = serverSocket.accept();//establishes connection
                LOGGER.info("Connection is established");
                /* Thread Pool */
                ExecutorService executor = Executors.newFixedThreadPool(5);
                HTTPHandle hh = new HTTPHandle(connection, ++PropertyConstants.countId);
                executor.execute(hh); //calling new thread
                executor.shutdown(); // shuting down completed thread
            }
        } catch (Exception e) {
            LOGGER.debug(e);
            e.printStackTrace();
        }
    }
}
