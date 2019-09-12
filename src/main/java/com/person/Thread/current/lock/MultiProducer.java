package com.person.Thread.current.lock;

public class MultiProducer implements Runnable {

    private ConditionExample conditionExample;
    private SynchronizedExample example;
    public MultiProducer(ConditionExample example){
        this.conditionExample = example;

    }
    public MultiProducer(SynchronizedExample example){
        this.example =example;
    }
    @Override
    public void run() {
       while (true){
           //conditionExample.product("北京烤鸭");
           example.product("北京烤鸭");
           //product("北京烤鸭");
       }
    }
    private void product(String name){
        synchronized (example){
            while(example.flag){
                try {
                    example.wait();
                } catch (InterruptedException e) {
                    System.out.println("当前线程进入等待阻塞状态");
                }
            }
            Integer inventoryNum = example.getInventoryNum();
            example.setName(name+inventoryNum);
            inventoryNum++;
            example.flag=true;
            System.out.println(Thread.currentThread().getName()+"生产者"+inventoryNum+example.getName());
            example.notifyAll();
        }
    }
}
