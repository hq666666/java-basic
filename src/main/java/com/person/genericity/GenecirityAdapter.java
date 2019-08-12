package com.person.genericity;

/**
 * 泛型实现类：
 *
 *      01：
 *          未传入泛型实参时，与泛型类的定义相同，在声明类的时候，
 *        需将泛型的声明也一起加到类中：例如：class GenecirityAdapter<T> implements GenecirityInterface<T>；
 *
 *     02：这种形式用于abstract class(抽象类)最为合适还可以封装其他属性(最好是公共使用的)让此抽象类作为父类；
 *
 */
public abstract class GenecirityAdapter<T extends Object > implements GenecirityInterface<T> {

    private String name;

    private  int age;

    public T getInstance() {
        this.name = "xiaoming";

        return (T)this.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
