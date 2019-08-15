package com.person.basic.classloader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class FileClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, MalformedURLException {
        /**
         * 测试自定类加载器
         */
        String path ="D:/mycode";
        FileClassLoader fileClassLoader = new FileClassLoader(path);
        //Class<?> example = fileClassLoader.loadClass("Example");
        /**
         * 使用自定义findClass方法：
         *          作用：
         *             绕过ClassLoader中loadClass()方法体中调用findLoadedClass()方法进行了检测是否已被加载；
         */
        Class<?> example = fileClassLoader.findClass("Example");
        Object o = example.newInstance();
        System.out.println("非本地文件类的对象的类加载器："+o.getClass().getClassLoader());

        /**
         * 测试URL自定义类加载器
         */
        File file = new File(path);
        URI uri = file.toURI();
        URL[] url = {uri.toURL()};
        FileURLClassLoader fileURLClassLoader = new FileURLClassLoader(url);
        Class<?> example1 = fileURLClassLoader.loadClass("Example");
        Object o1 = example1.newInstance();
        System.out.println("获取自定义URL类加载器："+o1.getClass().getClassLoader());
    }
}
