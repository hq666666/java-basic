package com.person.annotations;

/**
 * 数据库表Member对应实例bean
 */
@DBTable(name = "MEMBER")
public class Member {
    @SQLString(name = "ID",value = 30,CONSTRAINT = @Constraint(primaryKey = true))
    private String id;
    @SQLString(name = "NAME",value = 32)
    private String name;
    @SQLInteger(name = "AGE")
    private int age;
    @SQLString(name = "DESCRIPTION",value = 200,CONSTRAINT = @Constraint(allowNull = true))
    private String description;


}
