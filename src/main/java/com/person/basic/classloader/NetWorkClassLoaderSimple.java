package com.person.basic.classloader;

/**
 * 类加载器：
 *          范例实体类
 */
public class NetWorkClassLoaderSimple {

    private NetWorkClassLoaderSimple simple;

    public  void setSimple(Object object){
        this.simple = (NetWorkClassLoaderSimple)object;
        System.out.println(simple.toString());
    }
}
