package com.person.IO.BIO;

import java.io.IOException;
import java.util.Random;

public class BIOTest {

    public static void main(String[] args) throws InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BIOServer.start();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }).start();
        Thread.sleep(1000);
        char [] operator = {'+','-','*','/'};
        Random random = new Random();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    String express = random.nextInt(10)+""+operator[random.nextInt(4)]+(random.nextInt(10)+1);
                    try {
                        BIOClient.send(express);
                        Thread.sleep(random.nextInt(1000));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

            }
        }).start();
    }
}
