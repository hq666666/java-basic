package com.person.genericity;

import java.util.Map;

/**
 * 泛型类
 * @param <T>
 */
public class Genericity<T> {

    private T t;

    public  void setT(T t){
        this.t=t;
    }

    public T getT(){
        return t;
    }
    //Number是所用基本类型的父类；
   /* public void showKeyValue1(Genericity<Number> obj){
        System.out.println("泛型测试"+obj.getT());
    }*/
    public void showKeyValue1(Genericity<?> obj){
        System.out.println("泛型测试"+obj.getT());
    }

    /**
     * 泛型方法：
     *
     *      01：修饰符(public、default、static、final等)到返回值之间声明泛型类型；
     *      02：泛型类型数量可以声明多个
     *
      * @param <E>
     * @return
     */
    public static <U> void getStr(U para){
        System.out.println(para);
    }

    public <E> E getInfo(E param){
        if(null == param){
            return null;
        }
        return param;
    }
    public <L> void out(L...te){
        System.out.println(te);
    }
    public <P> P getArray(P...pa){
        return (P) pa;
    }
    public static<K,V> Map<K,V> getMap(Map<K,V> kvMap){

        for(Map.Entry map:kvMap.entrySet() ){
            System.out.println(map.getKey()+"=============="+map.getValue());
        }
        return kvMap;
    }
    public static<T> void print(T pa){
        System.out.println("静态方法时必须在方法中声明，即便类已声明相同的泛型类型但无法识别");
    }


}
