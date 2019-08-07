package com.person.test;

import com.person.basic.NetWorkClassLoader;
import com.person.basic.SerializableImpl;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author hq
 * 基础单元测试
 */
public class BasicTest {

    /**
     * serializable:字节流写出操作
     */
    @Test
    public void test01() {

        List<String> list = Arrays.asList("java", "80", "90", "86");

        int c = Integer.parseInt(list.get(1));
        int e = Integer.parseInt(list.get(2));
        int m = Integer.parseInt(list.get(list.size() - 1));

        SerializableImpl example = new SerializableImpl(list.get(0), c, e, m);
        example.printData();
        try {
            FileOutputStream os = new FileOutputStream("C:\\Data.txt");
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(example);
            oos.flush();
            oos.close();
            os.close();
        } catch (Exception exce) {
            System.out.println(exce);
        }

    }
    /**
     * serializable:字节流读取操作
     */
    @Test
    public void test02() {

        try {
            FileInputStream is = new FileInputStream("C:\\Data.txt");
            ObjectInputStream ois = new ObjectInputStream(is);
            SerializableImpl result =  (SerializableImpl)ois.readObject();
            result.printData();
            ois.close();
            is.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * 类加载器测试类：测试没成功（错误示例暂时没解决）
     */
    @Test
    public void test03(){

        try {
            //测试加载网络中的class文件
            String rootUrl = "http://localhost:9090/basic/classes";
            String className = "com.person.basic.NetWorkClassLoaderSimple";
            NetWorkClassLoader ncl1 = new NetWorkClassLoader(rootUrl);
            NetWorkClassLoader ncl2 = new NetWorkClassLoader(rootUrl);
            Class<?> clazz1 = ncl1.loadClass(className);
            Class<?> clazz2 = ncl2.loadClass(className);
            Object obj1 = clazz1.newInstance();
            Object obj2 = clazz2.newInstance();
            clazz1.getMethod("setSimple", Object.class).invoke(obj1,obj2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}