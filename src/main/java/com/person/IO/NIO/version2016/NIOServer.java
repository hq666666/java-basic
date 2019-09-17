package com.person.IO.NIO.version2016;

public class NIOServer {

    private static int DEFAULT_PORT = 12345;
    private static NIOServerHandler serverHandler;

    public static void start(){
        start(DEFAULT_PORT);
    }

    private static void start(int defaultPort) {
        if(null!=serverHandler)
            //关闭服务器
            serverHandler.stop();
        serverHandler = new NIOServerHandler(defaultPort);
        //执行任务
        new Thread(serverHandler,"Server").start();
    }

    public static void main(String[] args) {
        start();
    }
}
