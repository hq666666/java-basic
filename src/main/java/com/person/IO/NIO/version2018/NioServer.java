package com.person.IO.NIO.version2018;

public class NioServer {

    private static int DEFAULT_PORT = 5656;
    private static NioServerService service;

    public static void main(String[] args) {
       start(DEFAULT_PORT);
    }

    private static void start(int defaultPort) {
        if(null !=service)
            service.stop();
        //启动NIOserver端
        new Thread(new NioServerService(defaultPort),"server").start();
    }


}
