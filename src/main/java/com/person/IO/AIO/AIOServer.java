package com.person.IO.AIO;

public class AIOServer {
    private static int DEFAULT_PORT = 5656;
    private static AsyncServerHandler serverHandler;
    public static volatile  long clientCount = 0;
    public static void start(){
        start(DEFAULT_PORT);
    }

    private static void start(int port) {
        if(null !=serverHandler)return;
        new Thread(new AsyncServerHandler(port),"Server").start();
    }

    public static void main(String[] args) {
        AIOServer.start();
    }

}
