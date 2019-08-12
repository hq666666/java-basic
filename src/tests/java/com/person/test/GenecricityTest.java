package com.person.test;

import com.person.genericity.GenecirityImpl;
import com.person.genericity.Genericity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * genericity (泛型)test：
 *
 *          01：泛型指适用于编译时期
 */
public class GenecricityTest {

    /**
     * 泛型：
     *     01: 将类型进行参数化，会根据具体传入的实参来做类型的限制
     *  泛型类：
     *     01：
     *          泛型类在类初始化对象时限制数据类型，则该对象中对应属性的数据类型只能是对象初始化的数据类型；
     *        若在类初始化对象没有限制数据类型，则该对象对应属性的数据类型泛指一切；
     *     02：
     *          泛型的类型不能是primitive type(原始类型)例如：int、boolean，但可以使用对应原始类型的包装类
     *       例如：Integer Boolean等；
     *
     *     03：不能对确切的泛型类型使用instanceof操作,编译时会出错
     *     04：同一种泛型可以对应多个版本（因为参数类型是不确定的），不同版本的泛型类实例(数据类型)是不兼容的
     *     05：泛型通配符：？(表示未知类型)
     *                 格式：变量<?>
     *                 作用：
     *                      当数据类型不确定的时候可以使用；
     */
    @Test
    public void test(){

        //-------------genericity 泛型simple example--------------------
        List<String> strings = new ArrayList<String>();
        List<Integer> integers = new ArrayList<Integer>();

        final  Class<? extends List> stringsClass = strings.getClass();
        final Class<? extends List> aClass = integers.getClass();
        if (stringsClass.equals(aClass)) System.out.println("泛型只适用于编译时期的检查");

        //----------泛型类的测试----------------

        //不在类初始化对象时限制类型
        Genericity genericity = new Genericity();

        genericity.setT("hello");
        System.out.println("传入字符串类型的实参"+genericity.getT());
        genericity.setT(123456);
        System.out.println("传入integer数据类型的实参"+genericity.getT());

        //在类初始化时限制数据类型
        Genericity<String> stringGenericity = new Genericity<String>();
        //在类初始化时限制数据类型，只能传规定的数据类型；
        //stringGenericity.setT(123456);//传其他类型编译时期直接报错
        stringGenericity.setT("haha");
        System.out.println(stringGenericity.getT());
        //不能对确切的泛型类型使用instanceof操作
       //if("haha" instanceof Genericity<String>)
        Genericity<Number> numberGenericity = new Genericity<Number>();
        numberGenericity.setT(456);
        numberGenericity.showKeyValue1(numberGenericity);
        Genericity<Integer> integerGenericity = new Genericity<Integer>();
        integerGenericity.setT(123);
        //同一种泛型可以对应多个版本（因为参数类型是不确定的），不同版本的泛型类实例是不兼容的
       // integerGenericity.showKeyValue1(integerGenericity);
        /**
         * 改变该类showKeyValue1形参的泛型类型：Genericity<?>
         *      泛型格式：
         *              <?>：
         *                 ?:是泛型的数据类型通配符
         *                     作用：
         *                          在数据数据类型不确定是可以使用？来表示；
         *
         */
        integerGenericity.showKeyValue1(integerGenericity);//编译通过
    }

    /**
     *泛型接口：
     *：
     *      01：
     *          接口是作为行为规范使用，而泛型接口就是在该接口中对应的方法中可以获取
     *      任意数据类型的数据；
     *
     *     02：使用抽象类实现接口(作用：做一层封装，也可以定义一些公共的属性)根据场景决定是否创建该类；
     *     03：创建该接口的实现类（建议：指定某一数据类型），(若继承实现该接口的抽象类按具体的业务抉择是否重写该方法或实现该方法)；
     *
     * 泛型方法：
     *
     *      01： 修饰符(public、default、static、final等)到返回值之间声明泛型声明泛型类型
     *      02：泛型类型数量可以声明多个；
     *      03：若在静态方法使用泛型是无法识别类级别声明的泛型类型，必须在静态方法中声明(可以声明与类级别相同的泛型类型)
     *  泛型上下边界：
     *
     *         01: 在使用泛型的时候，可以通过传入的泛型类型实参尽心上下边界的限制
     *         例如：<T extends Object>
     *
     *         02:泛型的上下边界添加，必须与泛型的声明在一起
     *
     */
    @Test
    public void  test1(){

        //--------genecirity Interface test-----------
        GenecirityImpl genecirity = new GenecirityImpl();
        String instance = genecirity.getInstance();
        System.out.println(instance.equals(genecirity.getName()));
        //---------泛型方法(genecirity method)--------
        Genericity genericity = new Genericity();
        //--------单个泛型声明-----------
        String[] input = new String[]{"apple","banana","furit"};
        Object info = genericity.getInfo(input);
        System.out.println(info.toString());
        if(info instanceof String[]){
            String [] output =(String[])info;
            for(String out:output){
                System.out.println(out);
            }
        }
        //---------返回为void-------------
        Genericity.getStr("Drogon");
        //---------泛型可变参数-----------
        genericity.out("haha","hehe");
        Object array = genericity.getArray(123, 456, 789);
        if(array instanceof String[]){
            String[] result = (String[])array;
            for(String str:result){
                System.out.println(str);
            }
        }
        //-------多个泛型类型声明---------
        Map<String, String> result1 = new LinkedHashMap<String, String>();
        Map<String, Boolean> result2 = new LinkedHashMap<String, Boolean>();
        result1.put("1","one");
        result1.put("2","two");
        result1.put("3","three");
        result2.put("1",true);
        result2.put("2",false);
        result2.put("3",true);

        Genericity.getMap(result1);
        Genericity.getMap(result2);
        //类型不符合
        //genericity.getExtends(new Object());
        //--------静态方法泛型----------
        Genericity.print(123);
    }
}
