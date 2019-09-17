package com.person.IO.NIO.version2018;

public class NioClient {

    private static final int DEFAULT_PORT = 5656;
    private static final String ADDRESS = "127.0.0.1";
    public static void main(String[] args) {

            new Thread(new NioClientService(ADDRESS,DEFAULT_PORT),"Client").start();

    }
}
