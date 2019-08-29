package com.person.Thread.current;

public class User {

    private String name;

    private Integer age;

    private static String id ="USER_ID";

    public User(){
        System.out.println("调用user的构造器");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        User.id = id;
    }



}
