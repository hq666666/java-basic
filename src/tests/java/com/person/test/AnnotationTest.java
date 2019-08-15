package com.person.test;

import com.person.annotations.*;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class AnnotationTest {
    /**
     * 反射对于注解的应用；
     *
     *    注意：
     */
    @Test
    public  void test(){
        //通过isAnnotationPresent方法判断指定注解类型是否应用到当前类对象
        boolean flag = Example.class.isAnnotationPresent(Auth.class);
        if(flag){
            //通过getAnnotation方法获取指定注解的对象；
            Auth annotation = Example.class.getAnnotation(Auth.class);
            System.out.println("id="+annotation.id());
            System.out.println("msg="+annotation.msg());
        }
    }
    @Test
    public  void test2() throws ClassNotFoundException {
        String className = "com.person.annotations.Member";
        String result = createTableSql(className);
        System.out.println("Table Creation SQL for "+className+"is:\n"+result);
    }

    private String createTableSql(String className) throws ClassNotFoundException {
        Class<?> acl = Class.forName(className);
        DBTable dbTable = acl.getAnnotation(DBTable.class);
        if(null == dbTable){
            System.out.println("NO DBTable Annotation in class"+className);
            return  null;
        }
        String tableName = dbTable.name();
        //判断是否有值，可以使用当前类名作为表明
        if(tableName.length()<1)
            tableName = acl.getName().toUpperCase();
        ArrayList<String> strings = new ArrayList<String>();
        //获取该类的所有字段
        for(Field field:acl.getDeclaredFields()){
            String cloumnName = null;
            Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
            System.out.println(declaredAnnotations);
            if(declaredAnnotations.length<1)continue;

            if(declaredAnnotations[0] instanceof SQLInteger){
                SQLInteger  cloumnInteger = (SQLInteger) declaredAnnotations[0];
                //获取字段名称
                if(cloumnInteger.name().length()<1)
                    cloumnName=field.getName().toUpperCase();
                else
                    cloumnName=cloumnInteger.name();

                strings.add(cloumnName+" INT "+getConstraint(cloumnInteger.CONSTRAINT()));
            }

            if(declaredAnnotations[0] instanceof SQLString){
                SQLString cloumnString = (SQLString) declaredAnnotations[0];
                if(cloumnString.name().length()<1)
                    cloumnName =field.getName().toUpperCase();
                else
                    cloumnName = cloumnString.name();
                strings.add(cloumnName+" VARCHAR("+cloumnString.value()+") "+getConstraint(cloumnString.CONSTRAINT()));
            }
        }
        //数据库表构建语句
        StringBuilder createCommand = new StringBuilder("CREATE TABLE"+tableName+"(");
        for(String str:strings){
            createCommand.append("\n "+str+",");
        }
        String tableCreate = createCommand.substring(0,createCommand.length()-1)+")";
        return tableCreate;

    }

    /**
     * 判断该字段是否有其他约束
     * @param constraint
     * @return
     */
    private String getConstraint(Constraint constraint) {
        String constraints ="";
        if(!constraint.allowNull())
            constraints+="NOT NULL";
        else
            constraints+="NULL";
        if(constraint.primaryKey())constraints+="PRIMARY KEY";
        if(constraint.unique())constraints+="UNIQUE";
        return constraints;
    }
}
