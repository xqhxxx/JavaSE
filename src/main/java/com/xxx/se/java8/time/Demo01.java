package com.xxx.se.java8.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author xqh
 * @date 2022/8/16  16:08:53
 * @apiNote 循环读取区间数据
 */
public class Demo01 {
    public static void main(String[] args) {

        String _s = "2022-05-14 00:00:00";
        String _e = "2022-05-15 23:59:59";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime start_dt = LocalDateTime.parse(_s, df);
        LocalDateTime end_dt = LocalDateTime.parse(_e, df);

        while (start_dt.isBefore(end_dt)) {
            LocalDateTime next_dt = start_dt.plusDays(1);
            System.out.println("执行当前时间区间：" + start_dt + " " + next_dt.minusSeconds(1));
            //东八区 时间戳
            //0ms  ->北京时间：1970-01-01 08:00:00 转换为时间再+8h
            long ltm = start_dt.toInstant(ZoneOffset.of("+8")).toEpochMilli();
//            long ltm = start_dt.atZone(ZoneId.systemDefault()).toInstant();
            System.out.println(new Date(ltm));//时间戳转date 默认当前时区

            start_dt = next_dt;
        }


    }

}
