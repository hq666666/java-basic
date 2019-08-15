package com.person.basic.classloader;

import java.io.*;
import java.net.URL;

/**
 * 自定义内网络加载器：
 *
 *      必须继承ClassLoader类
 */
public class NetWorkClassLoader extends ClassLoader {

    private  String url;

    public NetWorkClassLoader(String url){

         this.url = url;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getClassData(name);
        if(null == classData && classData.length < 0){
            throw new ClassNotFoundException();
        }else {
            return this.defineClass(name,classData,0,classData.length);
        }

    }
    private byte[] getClassData(String name) {
        InputStream is = null;
        try {
            String path = classNameToPath(name);
            URL url = new URL(path);
            byte[] buff = new byte[1024*4];

            is =url.openStream();
            int len = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while((len = is.read(buff)) != -1) {
                baos.write(buff,0,len);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private String classNameToPath(String name) {

        return url+"/"+name.replace(",","/")+".class";
    }

}
