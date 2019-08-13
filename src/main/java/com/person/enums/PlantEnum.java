package com.person.enums;

/**
 * Enum（枚举）：
 *      出现的原因：
 *
 *          01：类型安全(枚举类型没有可以访问的构造器)；
 *          02：便捷提高了代码的可读性
 *     书写规范：
 *          01：在Enum class中需先定义指定的Field(即你想定义来写常量)；
 *          02：声明构造器：必须声明(通过此构造器实例化常量实例)
 *                  作用：
 *                        限制数量
 *          03：定义Enum constant；
 *                  注意：
 *                      Enum Constant定义结束要以  ;符号结束,多个常量以 ,符号分隔
 */
public enum PlantEnum {
    MERCURY(3.302,2.439),
    VERNUS(4.869,6.052),
    EARTH(5.975,6.378);
    private Double mass;

    private Double radius;

    PlantEnum(Double mass, Double radius) {
        this.mass = mass;
        this.radius = radius;
    }

    public Double getMass() {
        return mass;
    }

    public Double getRadius() {
        return radius;
    }

    public Double total(Double mass, Double radius){
        return  mass*radius;
    }
}
