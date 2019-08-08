package com.person.test;

import com.person.basic.TypeImpl;
import com.person.basic.annotation.Auth;
import com.person.basic.classloader.NetWorkClassLoaderSimple;
import com.person.basic.serializable.SerializableImpl;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.TypeVariable;
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
     * JDK-API:
     *      CLASS-API应用与测试
     *      类对象所共享的所有相同的数组元素类型和数量的维度
     */
    @Test
    public  void  test03() throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        //获取类级对象的类名
        System.out.println(NetWorkClassLoaderSimple.class.getName());
        //数组操作:Class对象所有的方法是共享
        String [] str = new String[6];
        System.out.println(str.getClass().isArray());
        System.out.println(str.getClass().toString()+"==="+str.getClass().getName());
        //创建Class类对象参数为完全限定名
        System.out.println(Class.forName("java.lang.String"));
        //实例化对象
        NetWorkClassLoaderSimple netWorkClassLoaderSimple = NetWorkClassLoaderSimple.class.newInstance();
       System.out.println(String.valueOf(netWorkClassLoaderSimple));
        //判断该对象是否实例化
        System.out.println(NetWorkClassLoaderSimple.class.isInstance(netWorkClassLoaderSimple));
        //原始类型的判断（java的原始类型：八大基础类型和void）
        System.out.println(int.class.isPrimitive());
        //判断是否为注解类型
        System.out.println(Auth.class.isAnnotation());
        TypeVariable<Class<NetWorkClassLoaderSimple>>[] typeParameters = NetWorkClassLoaderSimple.class.getTypeParameters();
        System.out.println(String.valueOf(typeParameters));
        System.out.println(new TypeImpl().getTypeName());
    }

}