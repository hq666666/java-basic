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
        //获取对应的类加载器
        Class<?> clazz1 = ncl1.loadClass(className);
        Class<?> clazz2 = ncl2.loadClass(className);
        System.out.println(clazz1);
        System.out.println(clazz2);
        Object obj1 = clazz1.newInstance();
        Object obj2 = clazz1.newInstance();
        final ClassLoader classLoader = obj1.getClass().getClassLoader();
        final ClassLoader classLoader1 = obj2.getClass().getClassLoader();


        System.out.println("类加载器01："+classLoader);
        System.out.println("类加载器01父加载器："+classLoader.getParent());
        System.out.println("类加载器02："+classLoader1);
        System.out.println("类加载器02父加载器："+classLoader1.getParent());
        System.out.println("测试类类加载器："+netWorkClassLoaderTest.getClass().getClassLoader());
        System.out.println("测试类父类加载器："+netWorkClassLoaderTest.getClass().getClassLoader().getParent());
        clazz1.getMethod("setSimple", Object.class).invoke(obj1,obj1);
        System.out.println(clazz1.getClassLoader());

    }
}
