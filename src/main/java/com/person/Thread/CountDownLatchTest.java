package com.person.Thread;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    private static final CountDownLatch LATCH = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {

       /* for(int i=0;i<5;i++)
        {
            new Thread(new Entry(LATCH)).start();
        }*/

        System.out.println("bingo!");

    }
}
