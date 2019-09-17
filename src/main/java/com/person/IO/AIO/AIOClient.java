package com.person.IO.AIO;

import java.io.IOException;
import java.util.Scanner;

public class AIOClient {

    private static final String DEFAULT_ADDRESS = "127.0.0.1";
    private static final int DEFAULT_PORT =5656;
    private static AsyncClientHandler handler;
    public static void start() throws IOException {
        start(DEFAULT_ADDRESS,DEFAULT_PORT);
    }

    private static void start(String defaultAddress, int defaultPort) throws IOException {
            if(null != handler)return;;
            new Thread(new AsyncClientHandler(defaultAddress,defaultPort),"Client").start();
    }
    public static boolean sendMsg(String msg)
    {
        if(msg.equals("q"))return false;
        AsyncClientHandler.sendMsg(msg);
        return true;
    }
    public static void main(String[] args) throws IOException {
       while(true){

           AIOClient.start();
           System.out.println("请输入信息……");
           Scanner scanner = new Scanner(System.in);
           while (AIOClient.sendMsg(scanner.nextLine()));
       }
    }
}
