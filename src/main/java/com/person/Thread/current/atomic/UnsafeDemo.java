package com.person.Thread.current.atomic;

import com.person.Thread.current.User;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Unsafe测试类：
 */
public class UnsafeDemo {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        //通过Field得到该Field对应的具体对象，传入null是因为该Field为static；
        Unsafe unsafe = (Unsafe)field.get(null);
        System.out.println(unsafe);
        //通过allocateInstance直接创建对象

        User user = (User) unsafe.allocateInstance(User.class);
        if(null == user){
            System.out.println("user is null");
        }

        Class<? extends User> userClass = user.getClass();
        User user1 = userClass.newInstance();
        Field name = userClass.getDeclaredField("name");
        Field age = userClass.getDeclaredField("age");
        Field id = userClass.getDeclaredField("id");

        //获取实例变量name和age在对象内存中偏移量并设置值
        unsafe.putInt(user,unsafe.objectFieldOffset(age),18);
        unsafe.putObject(user,unsafe.objectFieldOffset(name),"osi");

        //获取静态属性值，返回对应的class对象
        Object fieldBase = unsafe.staticFieldBase(id);
        System.out.println("staticBase:"+fieldBase);

        //获取静态变量id的偏移量
        long staticFieldOffset = unsafe.staticFieldOffset(id);

        //获取静态变量的值
        System.out.println("属性ID的值："+unsafe.getObject(fieldBase,staticFieldOffset));

        //设置属性值
        unsafe.putObject(fieldBase,staticFieldOffset,"Seted_ID");
        //获取重新设置静态属性的值
        System.out.println(unsafe.getObject(fieldBase,staticFieldOffset));

        //输出user对象

        System.out.println("输出user："+user);


        Long data = 1000l;
        //单位字节
        byte size = 1;
        //调用allocateMemory分配内存，并获取内存地址memoryAddress
        long memoryAddress = unsafe.allocateMemory(size);
        //直接往内存写入数据
        unsafe.putAddress(memoryAddress,data);
        //获取指定内存地址的数据
        long addrData = unsafe.getAddress(memoryAddress);
        System.out.println("addrData"+addrData);
        String [] strings = {"haha","hehe","banana"};
        //数组操作：获取偏移地址和内存空间
        int baseOffset = unsafe.arrayBaseOffset(strings.getClass());
        int indexScale = unsafe.arrayIndexScale(strings.getClass());
        System.out.println(baseOffset+"\t"+indexScale);

        //boolean flag = unsafe.compareAndSwapInt(user, unsafe.objectFieldOffset(age), 18, 22);
        unsafe.getAndSetInt(user,unsafe.objectFieldOffset(age),22);
        int andAddInt = unsafe.getAndAddInt(user, unsafe.objectFieldOffset(age), 18);
        System.out.println(andAddInt);


    }
}
