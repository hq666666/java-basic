package com.person.enums;

/**
 *
 *
 *  模拟员工加班报酬；
 */
public enum PayEnum {
    MONDAY("workDay",200.0,8),
    TUESDAY("workDay",200.0,8),
    WEDHESDAY("workDay",200.0,8),
    THURSDAY("workDay",200.0,8),
    FRIDAY("workDay",200.0,8),
    SATURDAY("dayOff",350.0,8),
    SUNDAY("dayOff",350.0,8);

    private String payType;
    private Double payRate;
    private Integer standardTime;
    PayEnum(String payType, Double payRate,Integer standardTime) {
        this.payType = payType;
        this.payRate = payRate;
        this.standardTime=standardTime;
    }

    public String getPayType() {
        return payType;
    }
    public Double getPayRate() {
        return payRate;
    }
    public double pay(double workhour,double payRate){
         if(workhour <= standardTime){
             double pay = standardTime*payRate;
             return  pay;
         }
        double pay = standardTime*payRate;
        return  pay+overTimePay(workhour,payRate);
    }

    private double overTimePay(double workhour, double payRate) {
        return (workhour-standardTime)*payRate;
    }


    public Integer getStandardTime() {
        return standardTime;
    }
}
