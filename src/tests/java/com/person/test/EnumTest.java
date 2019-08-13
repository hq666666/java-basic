package com.person.test;

import com.person.enums.Color;
import com.person.enums.Meal;
import com.person.enums.OperationEnum;
import com.person.enums.PlantEnum;
import org.junit.Test;

public class EnumTest {
    /**
     * 枚举测试:
     *
     *    测试得出以下结论：
     *
     *         01: 引用Enum类的常量就是获取对应的常量对象；
     *         02：获取指定的常量属性值和Enum类非静态方法：
     *              是通过常量对象获取指定的属性值以及调用Enum类的方法；
     *
     *
     *
     *
     */
    @Test
    public void test(){
        //---------枚举类的常用方法----------

        //通过valueOf方法获取该Enum类的指定枚举类型
        PlantEnum plantEnum = PlantEnum.valueOf("EARTH");//EARTH
        PlantEnum earth1 = PlantEnum.valueOf(PlantEnum.class, "EARTH");//EARTH
        //通过values方法获取该Enum类所有的枚举类型
        PlantEnum[] values = PlantEnum.values();


        //----------枚举的第一种形式---------------
        //获取Enum类常量对象
        PlantEnum earth = PlantEnum.EARTH;
        System.out.println(earth);
        //获取Enum类常量对象的指定属性值；
        Double mass = PlantEnum.EARTH.getMass();//5.975
        Double radius = PlantEnum.EARTH.getRadius();//6.378
        //调用Enum类常量对象的方法
        Double total = PlantEnum.EARTH.total(mass, radius);//38.10855

        //------------枚举的第二种形式-----------------
        OperationEnum plus = OperationEnum.PLUS;
        double x = 2.0;
        double y =4.0;
        Double apply = plus.apply(x, y);//6.0
        //-------第三种形式--------
        Meal.Food[] values1 = Meal.APPETIZER.getValues();//SALAD，SOUP，SPRING_ROLLS
        if(null != values1){
            for(Meal.Food var:values1){
                System.out.println(var);
            }
        }
    }


    @Test
    public void test1(){
        /**
         *  Enum与switch
         */
        Color bule = Color.BULE;
        switch (bule){
            case RED:
                System.out.println("红色");
                break;
            case BULE:
                System.out.println("蓝色");
                break;
            case GREEN:
                System.out.println("绿色");
                break;
             default:
                 System.out.println("白色");
        }


    }
}
