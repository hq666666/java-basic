package com.person.IO.NIO.version2016;

import java.io.IOException;
import java.util.Scanner;

public class NIOTest {

    public static void main(String[] args) throws InterruptedException, IOException {
        NIOServer.start();
        Thread.sleep(100);
        NIOClient.start();
        while (NIOClient.sendMsg( new Scanner(System.in).nextLine()));
    }
}
