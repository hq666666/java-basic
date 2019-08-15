package com.person.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解的数据类型声明
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AnnotationElementDemo {

    //枚举类型
    enum Statu{ative,stop;};

    //声明枚举
    Statu statu() default Statu.stop;

    //布尔类型
    boolean flag()default false;

    //string类型
    String  msg()default "";

    //class类型
    Class<?> CLASS()default Void.class;

    //注解嵌套
    Auth AUTH() default @Auth(id =2 );

    //数组类型
    long[] value();
}
