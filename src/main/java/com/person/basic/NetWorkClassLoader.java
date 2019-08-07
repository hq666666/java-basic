package com.person.basic;

/**
 * 自定义内加载器：
 *
 *      必须继承ClassLoader类
 */
public class NetWorkClassLoader extends ClassLoader {

    private  String url;

    public NetWorkClassLoader(String url){

         this.url = url;
    }

}
