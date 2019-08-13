package com.person.enums;

/**
 * 特定常量的方法：(具体要根据业务)
 *      模拟计算机加减乘除
 *     规范格式：
 *          Enum constant可以通过{}重写在Enum类定义的方法；
 *        注意：
 *             关于访问修饰符选择
 *     作用：与interface类似
 *
 *          通过声明一个公共抽象的方法，根据不同常量对象有具体的实现
 *
 */
public enum OperationEnum {
    PLUS("+"){public Double apply(double x, double y){return x+y;}},
    MINUS("-"){public Double apply(double x, double y){return x-y;}},
    TIMES("*"){public Double apply(double x, double y){return x*y;}},
    DIVIDE("/"){public Double apply(double x, double y){return x/y;}};
    private String symbol;

    OperationEnum(String symbol) {
        this.symbol = symbol;
    }

   public  Double apply(double x,double y){return null;}

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "OperationEnum{" +
                "symbol='" + symbol + '\'' +
                '}';
    }
}
