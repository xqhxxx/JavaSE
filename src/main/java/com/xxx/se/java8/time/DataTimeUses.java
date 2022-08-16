package com.xxx.se.java8.time;

import java.time.LocalDate;

/**
 * @author xqh
 * @date 2022/8/16  14:47:20
 * @apiNote
 *
 * java.time 包下的所有类都是不可变类型而且线程安全。
 */
public class DataTimeUses {
    public static void main(String[] args) {

        //1.获取当前日期
        LocalDate current_day = LocalDate.now();
        System.out.println("1、当前日期："+current_day);

        //2、获取年月日
        current_day.getYear();
        current_day.getMonthValue();
        current_day.getDayOfMonth();

        //3 指定日期
        LocalDate ud_day = LocalDate.of(2022, 5, 14);
        System.out.println("自定义日期："+ud_day);

        //4 比较日期相等
        if(current_day.equals(ud_day)){
            System.out.println("时间相等");
        }else{
            System.out.println("时间不等");
        }


    }
}
