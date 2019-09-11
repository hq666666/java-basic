package com.person.Thread.current.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLockExample {

    static AtomicReference<Thread> atRf = new AtomicReference<>();
    //自旋锁，当不是当前线程是做空循环且次数不超过100次
    public void lock(){
        Thread current = Thread.currentThread();
        AtomicInteger count = new AtomicInteger(0);
        while (!atRf.compareAndSet(null,current)&& count.intValue() <=100){
            count.incrementAndGet();
        }
    }
    //解锁，将当前线程设置为null;
    public void unLock(){
        Thread current = new Thread();
        atRf.compareAndSet(current,null);
    }
}
