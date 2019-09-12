package com.person.Thread.current.lock;

/**
 * 生产者与消费者
 */
public class SynchronizedExample {




    //产品名称
   private String name;
   //库存数量
   private Integer inventoryNum = 0;
   //线程是否进入等待状态
    boolean flag = false;

    /**
     * 生产
     * @param name
     */
   public void product(String name){
       synchronized (this){
           while (flag){
               try {
                   this.wait();
               } catch (InterruptedException e) {
                   System.out.println("当前线程进入等待状态");
               }
           }
           this.name = name+inventoryNum;
           inventoryNum++;
           flag = true;
           System.out.println(Thread.currentThread().getName()+"生产者"+inventoryNum+this.name);
           this.notifyAll();

       }
   }

    /**
     * 消费
     */
   public  void consume(){
       synchronized (this){
           while (!flag){
               try {
                   this.wait();
               } catch (InterruptedException e) {
                   System.out.println("当前线程进入等待状态");
               }
           }
           this.name = name+inventoryNum;
           inventoryNum--;
           flag = false;
           System.out.println(Thread.currentThread().getName()+"消费者"+inventoryNum+this.name);
           this.notifyAll();

       }
   }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(Integer inventoryNum) {
        this.inventoryNum = inventoryNum;
    }
}
