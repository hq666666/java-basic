package com.person.Thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Entry implements Runnable {

    private CountDownLatch latch;

    public Entry(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            System.out.println("haha");
            long startTime = System.currentTimeMillis();
            //通过await是当前线程等待建议设置等待时间以及最好不要使用CountDownLatch(根据场景)
            latch.await(3, TimeUnit.SECONDS);
            latch.countDown();
            long endTime = System.currentTimeMillis();
            System.out.println((endTime-startTime)/1000+"s");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
