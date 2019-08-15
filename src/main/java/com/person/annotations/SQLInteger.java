package com.person.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解Integer类型的字段
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SQLInteger {
    //对应数据库的列名
    String name()default "";
    //对应约束
    Constraint CONSTRAINT() default @Constraint;
}
