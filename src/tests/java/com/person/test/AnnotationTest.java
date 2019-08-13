package com.person.test;

import com.person.annotations.Auth;
import com.person.annotations.Example;
import org.junit.Test;

public class AnnotationTest {
    /**
     * 反射对于注解的应用；
     *
     *    注意：
     */
    @Test
    public  void test(){
        //通过isAnnotationPresent方法判断指定注解类型是否应用到当前类对象
        boolean flag = Example.class.isAnnotationPresent(Auth.class);
        if(flag){
            //通过getAnnotation方法获取指定注解的对象；
            Auth annotation = Example.class.getAnnotation(Auth.class);
            System.out.println("id="+annotation.id());
            System.out.println("msg="+annotation.msg());
        }
    }
}
