package com.person.basic;

import java.lang.reflect.InvocationTargetException;

public class Test {

    public void show(String str){
        System.out.println(str);
    }
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Test test = new Test();

        Object invoke = test.getClass().getMethod("show", String.class).invoke(test, "haha");
        System.out.println(invoke.toString());
    }
}
