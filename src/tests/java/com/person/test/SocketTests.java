package com.person.test;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTests {

      @Test
    public void test(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket serverSocket = null;
                Socket accept = null;
                try {
                    serverSocket = new ServerSocket(8000);
                    accept = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int port = accept.getPort();
                System.out.println(port);
            }
        }).start();
        System.out.println("haha");
    }
}
