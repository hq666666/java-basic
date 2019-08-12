package com.person.genericity;

/**
 * GenecirityImpl:
 *
 *      继承GenecirityAdapter(实现了GenecirityInterface<T>泛型接口);
 *
 *      01:输入实参(数据类型)根据指定的数据类型对该方法或属性进行数据类型限定
 *
 */
public class GenecirityImpl extends GenecirityAdapter<String> {


    private String app;

    private String auth;


    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    @Override
    public String toString() {
        return "GenecirityImpl{" +
                "app='" + app + '\'' +
                ", auth='" + auth + '\'' +
                '}';
    }
}
