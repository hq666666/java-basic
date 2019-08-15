package com.person.basic.classloader;

import java.io.*;

/**
 * 自定义File类加载器
 */
public class FileClassLoader extends ClassLoader {

    private String filePath;

    public FileClassLoader(String filePath){
        this.filePath=filePath;
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
       byte[] results = getFileClassData(name);
       if(null == results ){
           throw new ClassNotFoundException("无法获取到文件资源路径");
       }else{
           return this.defineClass(name,results,0,results.length);
       }

    }

    private byte[] getFileClassData(String name) {
        InputStream is = null;
        String path = getFilePath(name);
        try {
            is = new FileInputStream(path);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte [] bytes = new byte[1024*4];
            int len =-1;
            if((len = is.read(bytes)) != -1){
                out.write(bytes,0,len);
            }
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  null;
    }

    private String getFilePath(String name) {
        return filePath+ File.separatorChar+name.replace(',',File.separatorChar)+".class";
    }
}
