package com.person.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解String类型：
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SQLString {
    //对应数据库的列名
    String name()default "";

    //列类型分配的长度
    int value() default -1;
    //对应约束
    Constraint CONSTRAINT()default @Constraint;
}
