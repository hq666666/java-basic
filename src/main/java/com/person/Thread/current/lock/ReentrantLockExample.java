package com.person.Thread.current.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample implements Runnable {

    static ReentrantLock lock = new ReentrantLock();
    public int result;
    @Override
    public void run() {
        System.out.println(lock.getQueueLength());
        for(int i=0;i<10;i++){
            lock.lock();
            lock.lock();
            try {
                result++;
            }finally{
                lock.unlock();
                lock.unlock();
            }
        }

    }

}
