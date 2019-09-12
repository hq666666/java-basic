package com.person.Thread.current.lock;

public class MultiConsumer implements  Runnable {

    private ConditionExample example;
    private SynchronizedExample example1;

    public MultiConsumer(ConditionExample example){
        this.example =example;
    }
    public MultiConsumer(SynchronizedExample example){
        this.example1 =example;
    }
    @Override
    public void run() {
        while (true){
            //example.consume();
            example1.consume();
            //consume();
        }
    }

    private void consume() {
        synchronized (example1){
            while (!example1.flag){
                try {
                    example1.wait();
                } catch (InterruptedException e) {
                    System.out.println("当前线程进入等待阻塞状态");
                }
            }
            Integer inventoryNum = example1.getInventoryNum();
            inventoryNum--;
            example1.flag =false;
            System.out.println(Thread.currentThread().getName()+"消费者"+inventoryNum+example1.getName());
            example1.notifyAll();
        }

    }
}
