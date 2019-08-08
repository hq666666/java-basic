package com.person.basic.serializable;

import java.io.Serializable;

/**
 * Serializable :序列化（就把对象转换成一串二进制串）
 *
 *          注意点：序列化与反序列化
 *
 */
public class SerializableImpl implements Serializable {

    /**
     * serialVersionUID：
     *
     *      概念：
     *
     *        01：序列化运行时同时每一个可序列化的类版本号,称为serialVersionUID；
     *
     *        02：erialVersionUID作用：
     *                  用于反序列化期间验证发送方和接收方的序列化对象加载的类的对象,是兼容的序列化
     *
     *        03：如果接收方加载一个类的对象有不同的serialVersionUID比相应的发送方的类,然后反序列化将导致一个InvalidClassException异常
     *
     *   总结：
     *          显示声明一个serialVersionUID的重要性，在于可识别是否属于同一类版本号，这样不会导致反序列化时出InvalidClassException异常；
     */
    private static final long serialVersionUID = -9165664299536108652L;

    private String name;
    private int chScore, engScore;
    //transient用于修饰希望在串行化过程中不想被串行化的属性
    private /*transient*/ int mathScore;

    public SerializableImpl(String n, int c,int e,int m)
    {
        name = n;
        chScore = c;
        engScore = e;
        mathScore = m;
    }

    public double avgScore()
    {
        return (chScore+engScore+mathScore)/3.0;
    }

    public void printData()
    {
        System.out.println("姓名："+name);
        System.out.println("语文："+chScore);
        System.out.println("英文："+engScore);
        System.out.println("数学："+mathScore);
        System.out.println("平均："+avgScore());

    }

}
