package com.person.basic.classloader;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * 自定义类加载器继承URLClassLoader类加载器
 */
public class FileURLClassLoader extends URLClassLoader {
    public FileURLClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public FileURLClassLoader(URL[] urls) {
        super(urls);
    }

    public FileURLClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }
}
