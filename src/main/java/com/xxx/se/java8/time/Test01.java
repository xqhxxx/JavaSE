package com.xxx.se.java8.time;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author xqh
 * @date 2022/8/16  17:01:23
 * @apiNote
 */
public class Test01 {
    public static void main(String[] args) {

        String _s = "1970-01-01 09:00:00";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start_dt = LocalDateTime.parse(_s, df);


        System.out.println("执行当前时间区间：" + start_dt);
        //东八区 时间戳
        //0ms  ->北京时间：1970-01-01 08:00:00   转换为时间再+8h
        long ltm = start_dt.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        //设置时区 去掉时区时间（-8h）再转换为时间戳
        //如果不-8h时区 转为时间戳（时间戳是相对0点的绝对差值） 再转回date（本地默认使用东八区） 又加了8h
        System.out.println(ltm);
        System.out.println(new Date(ltm));

        //如 北京时间：1970-01-01 00:00:00 其时间戳为：-28800000ms

        /**
         * 撇开工具等自动转换 ，手动计算时间戳和北京时间应该是
         *时间戳a  --》北京时间b： a转换为标准时间 再+8H
         *北京时间b--》时间戳a： b先-8H ，然后计算与19700101 0点的毫秒差
         */

    }
}
