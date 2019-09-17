package com.person.IO.AIO;

import java.io.IOException;
import java.util.Scanner;

public class AIOTest {
    public static void main(String[] args) throws InterruptedException, IOException {
        AIOServer.start();
        Thread.sleep(1000);
        AIOClient.start();
        System.out.println("请输入请求信息……");
        Scanner scanner = new Scanner(System.in);
        while (AIOClient.sendMsg(scanner.nextLine()));
    }
}
