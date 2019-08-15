package com.person.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Constraint（约束注解）
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Constraint {

    //判断是否为主键约束
    boolean primaryKey()default false;
    //判断是否允许为null
    boolean allowNull()default false;
    //判断是否唯一
    boolean unique()default false;

}
