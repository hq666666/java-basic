package com.person.IO.NIO.version2016;

import java.io.IOException;

public class NIOClient {

    private static String DEFAULT_ADDRESS = "127.0.0.1";
    private static int DEFAULT_PORT = 12345;
    private static NIOClientHandler clientHandler;

    public static void start(){
        start(DEFAULT_ADDRESS,DEFAULT_PORT);
    }

    private static void start(String defaultAddress, int defaultPort) {
        if(clientHandler != null)
            clientHandler.stop();
        clientHandler = new NIOClientHandler(defaultAddress,defaultPort);
        new Thread(clientHandler,"client").start();
    }

    public static boolean sendMsg(String msg) throws IOException {
        if(msg.equals("q"))return false;
        clientHandler.sendMsg(msg);
        return  true;
    }

}
