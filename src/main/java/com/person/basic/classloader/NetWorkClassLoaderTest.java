package com.person.basic.classloader;

/**
 * 类加载器测试类：测试没成功
 *            效果：
 *                  原先出的效果是通过反射同类不同类加载器加载会出现反射异常
 *          原因：由于在本地测试，最终的自定义的加载器没有引用到，而是让父类加载器App classloader加载到内存（双亲委任模型导致）
 */
public class NetWorkClassLoaderTest {


    public static void main(String[] args) throws Exception {

        NetWorkClassLoaderTest netWorkClassLoaderTest = new NetWorkClassLoaderTest();
        //测试加载网络中的class文件
        String rootUrl = "http://localhost:9090/basic/classes";
        String className = "com.person.basic.classloader.NetWorkClassLoaderSimple";
        NetWorkClassLoader ncl1 = new NetWorkClassLoader(rootUrl);
        NetWorkClassLoader ncl2 = new NetWorkClassLoader(rootUrl);
        /**
         * 获取对应的类对象：
         *          注意：
         *              若为本地class文件则不会有自定义类加载器进行加载，由于双亲委派模式会有其父类，
         *           一般有默认系统类加载器AppClassloader进行加载；
         */

        Class<?> clazz1 = ncl1.loadClass(className);
        Class<?> clazz2 = ncl2.loadClass(className);

        //通过不同类加载器实例化对象
        Object obj1 = clazz1.newInstance();
        Object obj2 = clazz1.newInstance();

        System.out.println("自定义类加载器："+ncl1);
        System.out.println("自定义类加载器的父类加载器："+ncl1.getParent());
        System.out.println("系统默认类加载器："+ClassLoader.getSystemClassLoader());
        System.out.println("系统默认类加载器父类："+ClassLoader.getSystemClassLoader().getParent());
        System.out.println("系统根加载器："+ClassLoader.getSystemClassLoader().getParent().getParent());
        System.out.println(obj1.hashCode()+"=============="+obj2.hashCode());
        clazz1.getMethod("setSimple", Object.class).invoke(obj1,obj1);


    }
}
