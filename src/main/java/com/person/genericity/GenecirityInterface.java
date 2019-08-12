package com.person.genericity;

/**
 * 泛型接口
 * @param <T>
 */
public interface GenecirityInterface<T extends Object> {

    public T getInstance();

}
